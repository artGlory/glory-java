package com.glory.gloryCacheRedis.cache.pubSub.channelSub;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.glory.gloryUtils.utils.ByteUtil;
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
        topicList.add(new ChannelTopic("channel-1"));
        topicList.add(new ChannelTopic("channel-test"));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] channel = message.getChannel();
        System.err.println("订阅---" + ByteUtil.bytesToHex(channel));
        String topic = new StringRedisSerializer().deserialize(channel);
        System.err.println("订阅---" + topic);
        byte[] body = message.getBody();
        System.err.println("订阅---" + ByteUtil.bytesToHex(body));
        Object temp = (Object) new GenericFastJsonRedisSerializer().deserialize(body);
        System.err.println("订阅---" + temp);
    }
}
