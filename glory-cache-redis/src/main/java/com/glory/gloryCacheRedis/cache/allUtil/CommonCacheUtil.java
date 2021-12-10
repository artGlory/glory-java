package com.glory.gloryCacheRedis.cache.allUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class CommonCacheUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    
}
