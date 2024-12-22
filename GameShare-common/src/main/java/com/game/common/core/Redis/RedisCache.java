package com.game.common.core.Redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCache {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public <T> void setCacheObject(final String key, final T value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getCacheObject(final String key)
    {
        return redisTemplate.opsForValue().get(key);
    }

    public void removeCacheObject(final String verifyCode) {
        redisTemplate.delete(verifyCode);
    }
}