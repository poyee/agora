package com.poyee.agora.vote;

import com.poyee.agora.entity.User;
import com.poyee.agora.entity.Vote;
import com.poyee.agora.entity.VoteId;
import com.poyee.agora.redis.RedisService;
import com.poyee.agora.user.LocalUser;
import com.poyee.agora.utils.RedisUtils;
import com.poyee.agora.vote.bean.VoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VoteService {
    private final VoteRepository repository;

    private final RedisService redisService;

    @Autowired
    public VoteService(VoteRepository repository,
                       RedisService redisService) {
        this.repository = repository;
        this.redisService = redisService;
    }

    public void vote(LocalUser localUser, VoteRequest voteRequest) {
        User user = localUser.getUser();
        List<Vote> oldVote = updateDbVote(user, voteRequest);

        updateRedisVote(oldVote, voteRequest);
    }

    public List<Vote> getUserSelectedVote(Long pollId, User user) {
        return this.repository.findAllById_PollIdAndUser(pollId, user);
    }

    public int getOptionVoteCount(Long pollId, Integer optionNumber) {
        return redisService.getCount(RedisUtils.getVoteKey(pollId, optionNumber));
    }

    public int getPollTotalVoteCount(Long pollId) {
        return redisService.getCount(RedisUtils.getPollTotalVoteKey(pollId));
    }

    private List<Vote> updateDbVote(User user, VoteRequest voteRequest) {
        List<Vote> oldVote = getUserSelectedVote(voteRequest.getPollId(), user);
        this.repository.deleteAllById(oldVote.stream()
                .map(Vote::getId)
                .collect(Collectors.toList())
        );

        repository.saveAll(toVotes(voteRequest, user));

        return oldVote;
    }

    private void updateRedisVote(List<Vote> oldVote, VoteRequest voteRequest) {
        Set<Integer> oldVoteNumber = oldVote.stream()
                .map(Vote::getId)
                .map(VoteId::getNumber)
                .collect(Collectors.toSet());

        Set<Integer> newVoteNumber = new HashSet<>(voteRequest.getOptionNumbers());

        // decrease old option vote number
        oldVoteNumber.stream()
            .filter(voteNumber -> !newVoteNumber.contains(voteNumber))
            .forEach(voteNumber -> decrVote(voteRequest.getPollId(), voteNumber));


        // increase new option vote number
        newVoteNumber.stream()
            .filter(voteNumber -> !oldVoteNumber.contains(voteNumber))
            .forEach(voteNumber -> incrVote(voteRequest.getPollId(), voteNumber));


        updatePollTotalVote(voteRequest.getPollId(), newVoteNumber.size() - oldVoteNumber.size());
    }

    private void incrVote(Long pollId, Integer optionNumber) {
        redisService.incr(RedisUtils.getVoteKey(pollId, optionNumber));
    }

    private void decrVote(Long pollId, Integer optionNumber) {
        redisService.decr(RedisUtils.getVoteKey(pollId, optionNumber));
    }

    private void updatePollTotalVote(Long pollId, long delta) {
        redisService.incr(RedisUtils.getPollTotalVoteKey(pollId), delta);
    }

    private List<Vote> toVotes(VoteRequest voteRequest, User user) {
        List<Vote> votes = new ArrayList<>();
        for (Integer optionNumber : voteRequest.getOptionNumbers()) {
            Vote vote = new Vote();
            vote.setUser(user);
            VoteId voteId = new VoteId();
            voteId.setPollId(voteRequest.getPollId());
            voteId.setNumber(optionNumber);
            vote.setId(voteId);
            votes.add(vote);
        }


        return votes;
    }
}
