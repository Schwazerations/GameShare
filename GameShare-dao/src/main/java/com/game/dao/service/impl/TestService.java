package com.game.dao.service.impl;

import com.game.common.core.Redis.RedisCache;
import com.game.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private RedisCache redisCache;

    public Result redis(){
        redisCache.setCacheObject("MyKey", "A_Random_Value");
        System.out.println(redisCache.getCacheObject("MyKey"));
        return Result.ok().data("MyKey", redisCache.getCacheObject("MyKey"));
    }
}
