package com.example.userservice.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisComponent {
    private final RedisTemplate<Object, Object> redisTemplate;
    public void addValue(Object redisKey, Object redisValue, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(redisKey, redisValue);
        redisTemplate.expire(redisKey, timeout, unit);
    }

    public Object getValue(Object redisKey) {
        return redisTemplate.opsForValue().get(redisKey);
    }
    public void deleteByKey(Object redisKey) {
        redisTemplate.delete(redisKey);
    }
}
