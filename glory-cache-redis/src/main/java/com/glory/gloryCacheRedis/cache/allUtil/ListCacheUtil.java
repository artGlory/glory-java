package com.glory.gloryCacheRedis.cache.allUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ListCacheUtil extends CommonCacheUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Blpop 命令移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     *
     * @param key
     * @param timeout
     * @return
     */
    public Object blPop(String key, long timeout) {
        return redisTemplate.opsForList().leftPop((String) key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 命令移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     *
     * @param key
     * @param timeout
     * @return
     */
    public Object brPop(String key, long timeout) {
        return redisTemplate.opsForList().rightPop((String) key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 通过索引获取列表中的元素
     *
     * @param key
     * @param index
     * @return
     */
    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取列表长度
     *
     * @param key
     * @return
     */
    public Long lIen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * Lpop 命令用于移除并返回列表的第一个元素。
     *
     * @param key
     * @return
     */
    public Object lPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 将一个或多个值插入到列表头部
     *
     * @param key
     * @return
     */
    public Long lPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * Lpushx 将一个值插入到已存在的列表头部，列表不存在时操作无效。
     *
     * @param key
     * @return
     */
    public Long lPushx(String key, Object value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * Rpop 命令用于移除并返回列表的最后一个元素。
     *
     * @param key
     * @return
     */
    public Object rPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 将一个或多个值插入到列表头尾
     *
     * @param key
     * @return
     */
    public Long rPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * Lpushx 将一个值插入到已存在的列表头部，列表不存在时操作无效。
     *
     * @param key
     * @return
     */
    public Long rPushx(String key, Object value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param key
     * @return
     */
    public List<Object> lPushx(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * Lrem 根据参数 COUNT 的值，移除列表中与参数 VALUE 相等的元素。
     * <p>
     * COUNT 的值可以是以下几种：
     * <p>
     * count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。
     * count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。
     * count = 0 : 移除表中所有与 VALUE 相等的值
     *
     * @param key
     * @return
     */
    public Long lRem(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * Redis Lset 通过索引来设置元素的值。
     * <p>
     * 当索引参数超出范围，或对一个空列表进行 LSET 时，返回一个错误。
     *
     * @param key
     * @param index
     * @param value
     */
    public void lSet(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * Ltrim 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     * <p>
     * 下标 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param key
     * @param start
     * @param end
     */
    public void lSet(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }


}
