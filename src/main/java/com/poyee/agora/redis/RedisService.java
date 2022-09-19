package com.poyee.agora.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public int getCount(String key) {
        ValueOperations<String, String> opt = redisTemplate.opsForValue();
        String count = opt.get(key);

        return count == null ? 0 : Integer.parseInt(count);
    }

    public void incr(String key) {
        incr(key, 1);
    }

    public void decr(String key) {
        incr(key, -1);
    }

    public void incr(String key, long delta) {
        ValueOperations<String, String> opt = redisTemplate.opsForValue();
        opt.increment(key, delta);
    }
}
