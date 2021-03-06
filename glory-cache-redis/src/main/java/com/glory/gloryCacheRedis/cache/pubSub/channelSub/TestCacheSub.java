package com.glory.gloryCacheRedis.cache.pubSub.channelSub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

public class TestCacheSub implements MessageListener {

    public static List<Topic> topicList;

    static {
        topicList = new ArrayList<>();
        topicList.add(new ChannelTopic("channel-test"));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] channel = message.getChannel();
        String channelDecode = new StringRedisSerializer().deserialize(channel);
        byte[] body = message.getBody();
        String bodyStrDecode = new StringRedisSerializer().deserialize(body);
        System.err.println("订阅---" + channelDecode);
        System.err.println("订阅---" + bodyStrDecode);

    }
}
