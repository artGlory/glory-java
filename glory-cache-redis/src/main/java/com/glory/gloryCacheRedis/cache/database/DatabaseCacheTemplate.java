package com.glory.gloryCacheRedis.cache.database;

import java.util.List;

/**
 * 数据库缓存操作接口.
 */

public interface DatabaseCacheTemplate {

    /**
     * 获取一个缓存对象;缓存默认过期时间为5分钟
     *
     * @param <T>
     * @param namespace
     * @param key       缓存存储key
     * @return 缓存对象
     */
    <T> T get(final String namespace, final String key, CacheCallback<T> callback);

    /**
     * 获取一个缓存对象
     *
     * @param <T>
     * @param namespace
     * @param key       缓存存储key
     * @return 缓存对象
     */
    public <T> T get(final String namespace, final String key, final long expireSeconds, CacheCallback<T> callback);


    <T> List<T> getList(final String namespace, final String key, CacheCallback<T> callback);

    /**
     * 获取缓存数据；
     *
     * @param namespace
     * @param key
     * @param expireSeconds
     * @param callback
     * @param <T>
     * @return
     */
    <T> List<T> getList(final String namespace, final String key, long expireSeconds, CacheCallback<T> callback);


    /**
     * 设置缓存
     */
    void setCache(String namespace, String key, Object t, long expireSeconds);

    /**
     * 设置缓存
     */
    void setCache(String namespace, String key, Object t);

    /**
     * 获取缓存
     */
    Object getCache(String namespace, String key);

    /**
     * 移除一个缓存对
     *
     * @param namespace
     * @param key       缓存对象key
     * @return 移除状态
     */
    boolean remove(final String namespace, final String... key);

    /**
     * 清除命名空间下的缓存数据
     *
     * @param namespace
     * @return 清除状态
     */
    boolean cleanSpace(final String namespace);
}

