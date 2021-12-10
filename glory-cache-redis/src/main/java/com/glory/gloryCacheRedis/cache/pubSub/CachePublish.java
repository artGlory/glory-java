package com.glory.gloryCacheRedis.cache.pubSub;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.glory.gloryUtils.utils.ByteUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    public void pubMessage(String channel, Object message) {
        System.err.println("发布--" + channel);
        System.err.println("发布--" + message);
        byte[] bytes = new GenericFastJsonRedisSerializer().serialize(message);
        System.err.println("发布--" + ByteUtil.bytesToHex(bytes));
        redisTemplate.convertAndSend(channel, new String(bytes));
        System.err.println("发布--" + ByteUtil.bytesToHex(new String(bytes).getBytes()));
    }
}
