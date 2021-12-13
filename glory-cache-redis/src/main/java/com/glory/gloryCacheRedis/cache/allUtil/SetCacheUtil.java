package com.glory.gloryCacheRedis.cache.allUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Component
public class SetCacheUtil extends CommonCacheUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 向集合添加一个或多个成员
     *
     * @param key
     * @param value
     * @return
     */
    public Long sAdd(String key, Object... value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 获取集合的成员数
     *
     * @param key
     * @return
     */
    public Long sCard(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * Redis Sdiff 命令返回第一个集合与其他集合之间的差异，也可以认为说第一个集合中独有的元素。不存在的集合 key 将视为空集。
     *
     * @param key
     * @return
     */
    public Set<Object> sDiff(String key, String... otherKey) {
        return redisTemplate.opsForSet().difference(key, Arrays.asList(otherKey));
    }

    /**
     * Sinter 命令返回给定所有给定集合的交集。 不存在的集合 key 被视为空集。 当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。
     *
     * @param key
     * @return
     */
    public Set<Object> sInter(String... key) {
        return redisTemplate.opsForSet().intersect(Arrays.asList(key));
    }

    /**
     * Sismember 命令判断成员元素是否是集合的成员。
     *
     * @param key
     * @return
     */
    public boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * Smembers 命令返回集合中的所有的成员。 不存在的集合 key 被视为空集合。
     *
     * @param key
     * @return
     */
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Smove 命令将指定成员 member 元素从 source 集合移动到 destination 集合。
     *
     * @param keySource
     * @param keyDestination
     * @param value
     * @return
     */
    public boolean sMove(String keySource, String keyDestination, Object value) {
        return redisTemplate.opsForSet().move(keySource, value, keyDestination);
    }

    /**
     * 返回所有给定集合的并集
     *
     * @param keys
     * @return
     */
    public Set<Object> sUnion(String... keys) {
        return redisTemplate.opsForSet().union(Arrays.asList(keys));

    }

}
