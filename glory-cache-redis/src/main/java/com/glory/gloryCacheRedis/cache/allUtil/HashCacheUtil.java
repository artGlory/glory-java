package com.glory.gloryCacheRedis.cache.allUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Component
public class HashCacheUtil extends CommonCacheUtil{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用于删除哈希表 key 中的一个或多个指定字段，不存在的字段将被忽略。
     *
     * @param key
     * @param filed
     * @return
     */
    public Long hDel(String key, String... filed) {
        return redisTemplate.opsForHash().delete(key, filed);
    }

    /**
     * Hexists 命令用于查看哈希表的指定字段是否存在。
     *
     * @param key
     * @param filed
     * @return
     */
    public Boolean hExists(String key, String filed) {
        return redisTemplate.opsForHash().hasKey(key, filed);
    }

    /**
     * Hget 命令用于返回哈希表中指定字段的值。
     *
     * @param key
     * @param filed
     * @return
     */
    public Object hGet(String key, String filed) {
        return redisTemplate.opsForHash().get(key, filed);
    }

    /**
     * Hkeys 命令用于获取哈希表中的所有域（field）。
     *
     * @param key
     * @return
     */
    public Set<Object> hKeys(String key) {
        Set<Object> keySet = redisTemplate.opsForHash().keys(key);
        return keySet;
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key
     * @return
     */
    public Long hLens(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * Hgetall 命令用于返回哈希表中，所有的字段和值。
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hGetAll(String key) {
        Map<Object, Object> result = new HashMap<>();
        Set<Object> keySet = redisTemplate.opsForHash().keys(key);
        for (Object o : keySet) {
            result.put(o, redisTemplate.opsForHash().get(key, o));
        }
        return result;
    }

    /**
     * Hmget 命令用于返回哈希表中，一个或多个给定字段的值。
     *
     * @param key
     * @param filedSet
     * @return
     */
    public Map<Object, Object> hMGet(String key, Set<Object> filedSet) {
        Map<Object, Object> result = new HashMap<>();
        Set<Object> keySet = filedSet;
        for (Object o : keySet) {
            result.put(o, redisTemplate.opsForHash().get(key, o));
        }
        return result;
    }

    /**
     * Hmset 命令用于同时将多个 field-value (字段-值)对设置到哈希表中。
     * <p>
     * 此命令会覆盖哈希表中已存在的字段。
     * <p>
     * 如果哈希表不存在，会创建一个空哈希表，并执行 HMSET 操作。
     *
     * @param key
     * @param map
     */
    public void hMSet(String key, Map<Object, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 将哈希表 key 中的字段 field 的值设为 value 。
     *
     * @param key
     * @param filed
     * @param value
     */
    public void hSet(String key, Object filed, Object value) {
        redisTemplate.opsForHash().put(key, filed, value);
    }

    /**
     * 只有在字段 field 不存在时，设置哈希表字段的值。
     *
     * @param key
     * @param filed
     * @param value
     */
    public void hSetNx(String key, Object filed, Object value) {
        redisTemplate.opsForHash().putIfAbsent(key, filed, value);
    }

}
