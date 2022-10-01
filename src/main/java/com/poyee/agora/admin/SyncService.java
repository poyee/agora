package com.poyee.agora.admin;

import com.poyee.agora.bean.ReactType;
import com.poyee.agora.comment.CommentRepository;
import com.poyee.agora.entity.Option;
import com.poyee.agora.entity.Poll;
import com.poyee.agora.poll.PollRepository;
import com.poyee.agora.react.ReactRepository;
import com.poyee.agora.redis.RedisService;
import com.poyee.agora.utils.RedisUtils;
import com.poyee.agora.vote.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SyncService {
    private static final Logger logger = LoggerFactory.getLogger(SyncService.class);

    @Autowired
    PollRepository pollRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ReactRepository reactRepository;

    @Autowired
    RedisService redisService;

    @Async
    @Transactional
    public void syncAllPollCount() {
        logger.info("start syncing all poll count");
        Iterable<Poll> polls = pollRepository.findAll();
        for (Poll poll : polls) {
            syncPollCount(poll);
        }
        logger.info("all poll count synced");
    }

    private void syncPollCount(Poll poll) {
        syncPollOptionsCount(poll);
        syncPollCommentCount(poll.getId());
        syncPollReactCount(poll.getId());
        logger.info("poll count {} synced", poll.getId());
    }
    
    private void syncPollOptionsCount(Poll poll) {
        int totalCount = 0;
        for (Option option : poll.getOptions()) {
            long count = voteRepository.countById_PollIdAndId_Number(poll.getId(), option.getNumber());
            redisService.set(RedisUtils.getVoteCountKey(poll.getId(), option.getNumber()), count);

            totalCount += count;
        }

        redisService.set(RedisUtils.getPollTotalVoteCountKey(poll.getId()), totalCount);
    }

    private void syncPollCommentCount(long pollId) {
        long commentCount = commentRepository.countByPollId(pollId);
        redisService.set(RedisUtils.getPollCommentCountKey(pollId), commentCount);
    }

    private void syncPollReactCount(long pollId) {
        for (ReactType reactType : ReactType.values()) {
            long reactCount = reactRepository.countByPollIdAndReact(pollId, reactType);
            redisService.set(RedisUtils.getReactCountKey(pollId, reactType.name()), reactCount);
        }
    }
}
