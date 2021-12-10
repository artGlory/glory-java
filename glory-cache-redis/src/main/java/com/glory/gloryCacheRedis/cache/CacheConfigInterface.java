package com.glory.gloryCacheRedis.cache;

/**
 * 缓存操作接口.
 */

public interface CacheConfigInterface {


    /**
     * 是否可用
     *
     * @return
     */
    boolean isEnableCache();

    /**
     * 获取key
     *
     * @param key
     * @return
     */
    String generateKey(final String... key);

    /**
     * 过期时间
     *
     * @return
     */
    long getTimeExpire(Long time);

    /**
     * 缓存前缀
     *
     * @return
     */
    String getKeyPrefix();


}

