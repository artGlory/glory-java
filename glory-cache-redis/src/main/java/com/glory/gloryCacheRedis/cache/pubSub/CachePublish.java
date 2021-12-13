package com.glory.gloryCacheRedis.cache.pubSub;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.glory.gloryUtils.utils.ByteUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CachePublish {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 发布消息
     *
     * @param channel
     * @param message
     */
    public void pubMessage(String channel, String message) {
        System.err.println("发布--" + channel);
        System.err.println("发布--" + message);
        redisTemplate.convertAndSend(channel, message);
    }
}
