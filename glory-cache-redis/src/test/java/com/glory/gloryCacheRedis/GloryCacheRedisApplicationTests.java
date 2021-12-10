package com.glory.gloryCacheRedis;

import com.glory.gloryCacheRedis.cache.pubSub.CachePublish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        String key="asdfsdf胡金龙";
        cachePublish.pubMessage("channel-1 ", result);
        cachePublish.pubMessage("channel-test ", result);
//        pubSubCacheUtilAbstract.pubMessage("tstasdf", result);
        Thread.sleep(10000);//jackson 反向序列化慢
        System.err.println("===================================");
    }

}
