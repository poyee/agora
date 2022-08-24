package com.poyee.agora.vote;

import com.poyee.agora.vote.bean.VoteRequest;
import com.poyee.agora.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    @Autowired
    private RedisTemplate<String, String> template;

    public void vote(VoteRequest voteRequest) {
        for (Integer optionNumber : voteRequest.getOptionNumbers()) {
            addVote(voteRequest.getPollId(), optionNumber);
        }
    }

    public int getVote(Long pollId, Integer optionNumber) {
        ValueOperations<String, String> opt = template.opsForValue();
        String voteNumber = opt.get(RedisUtils.getVoteKey(pollId, optionNumber));

        return voteNumber == null ? 0 : Integer.parseInt(voteNumber);
    }

    private void addVote(Long pollId, Integer optionNumber) {
        ValueOperations<String, String> opt = template.opsForValue();
        opt.increment(RedisUtils.getVoteKey(pollId, optionNumber));
    }
}
