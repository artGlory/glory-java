package com.glory.gloryCacheRedis;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.glory.gloryCacheRedis.cache.pubSub.CachePublish;
import com.glory.gloryCacheRedis.cache.pubSub.channelSub.TestCacheSub;
import com.glory.gloryUtils.utils.ByteUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class GloryCacheRedisApplicationTests {

    @Autowired
    CachePublish cachePublish;

    @Test
    void contextLoads() throws InterruptedException {


        System.err.println("===================================");
        Map<String, Object> result = new HashMap<>();
        result.put("123", "asdfjlj萨的房间里");
        result.put("12", "123");
        result.put("1", "123");
        result.put("23", "123");
        result.put("2", "123");
        result.put("3", "123");
        String key = "asdfsdf胡金龙";


//        byte[] bytes = new GenericFastJsonRedisSerializer().serialize(key);
//        System.err.println(ByteUtil.bytesToHex(bytes));
//      String  result = (String) new GenericFastJsonRedisSerializer().deserialize(bytes);
//        System.err.println(result);

        cachePublish.pubMessage(TestCacheSub.topicList.get(0).toString(), key);
        Thread.sleep(100000);//jackson 反向序列化慢
        System.err.println("===================================");
    }

}
