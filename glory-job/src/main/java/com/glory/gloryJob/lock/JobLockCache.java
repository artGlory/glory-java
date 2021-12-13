package com.glory.gloryJob.lock;

import java.util.Date;

/**
 * 分布式锁
 */
public interface JobLockCache {
    /**
     * 加锁
     *
     * @param lockName
     * @return
     */
    Boolean lock(String lockName);

    /**
     * 去锁
     *
     * @param lockName
     * @return
     */
    Boolean unlock(String lockName);

}
