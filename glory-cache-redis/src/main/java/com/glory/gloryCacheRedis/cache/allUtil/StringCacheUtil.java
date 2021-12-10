package com.glory.gloryCacheRedis.cache.allUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 缓存操作接口.
 */
@Component
public class StringCacheUtil {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Redis SET 命令用于设置给定 key 的值。如果 key 已经存储其他值， SET 就覆写旧值，且无视类型。
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取指定 key 的值。
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 命令用于获取存储在指定 key 中字符串的子字符串。
     * 字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String getRange(String key, int start, int end) {
        String result = redisTemplate.opsForValue().get(key, start, end);
        return result;
    }

    /**
     * 设置指定 key 的值，并返回 key 的旧值。
     *
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key, String value) {
        return (String) redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 返回所有(一个或多个)给定 key 的值。 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil 。
     *
     * @param key
     * @return
     */
    public String[] mGet(String... key) {
        List<String> keyList = Arrays.asList(key);
        return redisTemplate.opsForValue().multiGet(keyList).toArray(new String[key.length]);
    }

    /**
     * 指定的 key 设置值及其过期时间。如果 key 已经存在， SETEX 命令将会替换旧的值。
     *
     * @param key
     * @param value
     * @param timeout
     */
    public void setEx(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }
    /**
     * Redis Setnx（SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值。
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean setNx(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 获取指定 key 所储存的字符串值的长度。当 key 储存的不是字符串值时，返回一个错误。
     *
     * @param key
     * @return
     */
    public Long strLen(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * Mset 命令用于同时设置一个或多个 key-value 对。
     *
     * @param map
     */
    public void mSet(Map<String, String> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * Msetnx 命令用于所有给定 key 都不存在时，同时设置一个或多个 key-value 对。
     *
     * @param map
     */
    public Boolean mSetNx(Map<String, String> map) {
        return redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    /**
     * 自增操作
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     *
     * @param key
     * @return
     */
    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * Redis Incrby 命令将 key 中储存的数字加上指定的增量值。
     * <p>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
     *
     * @param key
     * @return
     */
    public Long incrBy(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * Redis Incrby 命令将 key 中储存的数字加上指定的增量值。
     * <p>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
     *
     * @param key
     * @return
     */
    public Double incrBy(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR  操作。
     *
     * @param key
     * @return
     */
    public Long decr(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * 命令将 key 中储存的数字减去指定的增量值。
     * <p>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR  命令。
     *
     * @param key
     * @return
     */
    public Long decrBy(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。
     * <p>
     * 如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样。
     *
     * @param key
     * @param appendStr
     * @return
     */
    public Integer append(String key, String appendStr) {
        return redisTemplate.opsForValue().append(key, appendStr);
    }

}

