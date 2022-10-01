package com.poyee.agora.react;

import com.poyee.agora.bean.ReactRequest;
import com.poyee.agora.entity.PollReact;
import com.poyee.agora.bean.ReactType;
import com.poyee.agora.entity.User;
import com.poyee.agora.redis.RedisService;
import com.poyee.agora.user.LocalUser;
import com.poyee.agora.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactService {
    @Autowired
    ReactRepository repository;

    @Autowired
    RedisService redisService;

    public void pollReact(Long pollId, ReactRequest request, LocalUser user) {
        removeOldReact(pollId, user.getUser());

        updateReact(pollId, request);
    }

    public int getPollReact(Long pollId, ReactType reactType) {
        return redisService.getCount(RedisUtils.getReactCountKey(pollId, reactType.name()));
    }

    public ReactType getUserPollReact(Long pollId, User user) {
        PollReact react = repository.findByPollIdAndUser(pollId, user);

        return react == null ? null : react.getReact();
    }

    private void removeOldReact(Long pollId, User user) {
        PollReact oldReact = repository.findByPollIdAndUser(pollId, user);

        if (oldReact != null) {
            this.repository.delete(oldReact);
            decrReactCount(pollId, oldReact.getReact().name());
        }
    }

    private void updateReact(Long pollId, ReactRequest request) {
        if (request.getReact() != null) {
            PollReact react = buildEntity(pollId, request);
            repository.save(react);
            incrReactCount(pollId, request.getReact());
        }
    }

    private void incrReactCount(Long pollId, String react) {
        redisService.incr(RedisUtils.getReactCountKey(pollId, react));
    }

    private void decrReactCount(Long pollId, String react) {
        redisService.decr(RedisUtils.getReactCountKey(pollId, react));
    }


    private PollReact buildEntity(Long pollId, ReactRequest request) {
        PollReact react = new PollReact();
        react.setPollId(pollId);
        react.setReact(ReactType.valueOf(request.getReact()));

        return react;
    }
}
