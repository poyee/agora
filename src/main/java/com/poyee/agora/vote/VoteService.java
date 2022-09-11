package com.poyee.agora.vote;

import com.poyee.agora.entity.User;
import com.poyee.agora.entity.Vote;
import com.poyee.agora.entity.VoteId;
import com.poyee.agora.user.LocalUser;
import com.poyee.agora.utils.RedisUtils;
import com.poyee.agora.vote.bean.VoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VoteService {
    private final VoteRepository repository;

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public VoteService(VoteRepository repository,
                       RedisTemplate<String, String> template) {
        this.repository = repository;
        redisTemplate = template;
    }

    public void vote(LocalUser localUser, VoteRequest voteRequest) {
        User user = localUser.getUser();
        List<Vote> oldVote = updateDbVote(user, voteRequest);

        updateRedisVote(oldVote, voteRequest);
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

    public List<Vote> getUserSelectedVote(Long pollId, User user) {
        return this.repository.findAllById_PollIdAndUser(pollId, user);
    }

    public int getOptionVote(Long pollId, Integer optionNumber) {
        ValueOperations<String, String> opt = redisTemplate.opsForValue();
        String voteNumber = opt.get(RedisUtils.getVoteKey(pollId, optionNumber));

        return voteNumber == null ? 0 : Integer.parseInt(voteNumber);
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
    }

    private void incrVote(Long pollId, Integer optionNumber) {
        ValueOperations<String, String> opt = redisTemplate.opsForValue();
        opt.increment(RedisUtils.getVoteKey(pollId, optionNumber));
    }

    private void decrVote(Long pollId, Integer optionNumber) {
        ValueOperations<String, String> opt = redisTemplate.opsForValue();
        opt.decrement(RedisUtils.getVoteKey(pollId, optionNumber));
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
