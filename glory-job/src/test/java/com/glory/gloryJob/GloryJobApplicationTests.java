package com.glory.gloryJob;

import com.glory.gloryJob.lock.JobLockCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class GloryJobApplicationTests {

    @Autowired
    @Qualifier("redisJobLockCacheImpl")
    private JobLockCache jobLockCache;

    @Test
    void contextLoads() {

        String str = "key0001";
        boolean result = jobLockCache.lock(str);

        System.err.println(result);
        if (result == false) {
            jobLockCache.unlock(str);
        }
    }

}
