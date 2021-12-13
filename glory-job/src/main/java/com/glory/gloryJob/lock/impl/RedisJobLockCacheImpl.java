package com.glory.gloryJob.lock.impl;

import com.glory.gloryCacheRedis.cache.allUtil.StringCacheUtil;
import com.glory.gloryJob.lock.JobLockCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 最大作用域为一个集群，集群使用一个redis
 */
@Slf4j
@Component(value = "redisJobLockCacheImpl")
@PropertySource(value = "classpath:application-job.properties")
public class RedisJobLockCacheImpl implements JobLockCache {

    /**
     * 锁最大作用时间
     */
    public static final long max_survival_seconed = 1000 * 60 * 10L;

    @Autowired
    private StringCacheUtil stringCacheUtil;

    @Override
    public Boolean lock(String lockName) {
        ZonedDateTime nowTime = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        boolean result = false;
        try {
            result = stringCacheUtil.setNx(lockName, nowTime.toString());
            if (result) {//成功
                result = true;
            } else {//失败
                result = false;
                String cacheTimeStr = stringCacheUtil.get(lockName);
                ZonedDateTime cacheTime = ZonedDateTime.parse(cacheTimeStr);
                if (cacheTime.toEpochSecond() - nowTime.toEpochSecond() > max_survival_seconed) {
                    if (unlock(lockName)) {
                        log.error("redis锁超过最大过期时间：{}s 强制删除锁；锁名称：{} 锁插入时间{} 当前时间{}", max_survival_seconed, lockName, cacheTimeStr, nowTime.toString());
                    }
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {

        }
        return result;
    }

    @Override
    public Boolean unlock(String lockName) {
        return stringCacheUtil.delete(lockName);
    }
}
