package com.glory.gloryCacheRedis.cache.database;

import com.glory.gloryCacheRedis.cache.CacheConfigInterface;
import com.glory.gloryCacheRedis.constants.CachePrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component(value = "databaseCacheTemplate")
@Slf4j
@PropertySource(value = "classpath:application-redis.properties")
@ConfigurationProperties(prefix = "spring.redis.cache.database")
public class RedisDatabaseCacheTemplate implements DatabaseCacheTemplate, CacheConfigInterface {

    @Value("${spring.redis.cache.enable:false}")
    private boolean mainEnable;
    @Value("${spring.redis.cache.expireMax:60}")
    private long expireMax;
    private boolean enable;
    private long expire;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public String generateKey(String... key) {
        String result = getKeyPrefix() + ".";
        for (String k : key) {
            result += k + ".";
        }
        return result.substring(0, result.length());
    }


    @Override
    public long getTimeExpire(Long time) {
        long result = expire;
        if (time != null)
            result = time;
        result = expire > expireMax ? expireMax : expire;
        return result;
    }

    @Override
    public String getKeyPrefix() {
        return CachePrefix.Database.getPrifix();
    }

    @Override
    public boolean isEnableCache() {
        return mainEnable && enable;
    }

    /**
     * 获取一个缓存对象;缓存默认过期时间为5分钟
     *
     * @param <T>
     * @param namespace
     * @param key       缓存存储key
     * @return 缓存对象
     */
    public <T> T get(final String namespace, final String key, CacheCallback<T> callback) {

        return get(namespace, key, expire, callback);
    }

    /**
     * 获取一个缓存对象
     *
     * @param <T>
     * @param namespace
     * @param key       缓存存储key
     * @return 缓存对象
     */
    public <T> T get(final String namespace, final String key, final long expireSeconds, CacheCallback<T> callback) {
        T t = null;
        try {
            if (!isEnableCache())
                return callback.getObject();//缓存未开启

            Object objectTemp = redisTemplate.opsForValue().get(generateKey(namespace, key));
            if (objectTemp != null) {
                t = (T) objectTemp;
            } else {
                if (callback == null) return null;
                else {
                    t = callback.getObject();
                    if (t != null) {
                        redisTemplate.opsForValue().set(generateKey(namespace, key), t, getTimeExpire(expireSeconds), TimeUnit.SECONDS);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                t = callback.getObject();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return t;
    }

    public <T> List<T> getList(final String namespace, final String key, CacheCallback<T> callback) {
        return getList(namespace, key, expire, callback);
    }

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
    public <T> List<T> getList(final String namespace, final String key, long expireSeconds, CacheCallback<T> callback) {
        List<T> t = null;
        try {
            if (!isEnableCache())
                return callback.getObjectList();

            Object objectTemp = redisTemplate.opsForValue().get(generateKey(namespace, key));
            if (objectTemp == null && callback == null) {
                return null;
            }
            if (objectTemp == null) {
                t = callback.getObjectList();
                if (t != null) {
                    redisTemplate.opsForValue().set(generateKey(namespace, key), t, getTimeExpire(expireSeconds), TimeUnit.SECONDS);
                }
            } else {
                t = (List<T>) objectTemp;
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                t = (List<T>) callback.getObject();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return t;
    }

    /**
     * 设置缓存
     */
    public void setCache(String namespace, String key, Object t, long expireSeconds) {
        if (!isEnableCache()) return;

        redisTemplate.opsForValue().set(generateKey(namespace, key), t, expireSeconds, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存
     */
    public void setCache(String namespace, String key, Object t) {
        setCache(namespace, key, t, expire);
    }

    /**
     * 获取缓存
     */
    public Object getCache(String namespace, String key) {
        if (!isEnableCache()) return null;

        Object objectTemp = redisTemplate.opsForValue().get(generateKey(namespace, key));
        return objectTemp;
    }

    /**
     * 移除一个缓存对
     *
     * @param namespace
     * @param key       缓存对象key
     * @return 移除状态
     */
    public boolean remove(final String namespace, final String... key) {
        if (!isEnableCache()) return true;

        boolean flag = false;
        List<String> keyList = new ArrayList<>();
        for (String k : key) {
            keyList.add(generateKey(namespace, k));
        }
        try {
            redisTemplate.delete(keyList);
            flag = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 清除命名空间下的缓存数据
     *
     * @param namespace
     * @return 清除状态
     */
    public boolean cleanSpace(final String namespace) {
        if (!isEnableCache()) return true;

        boolean flag = false;
        try {
            Set<String> keys = redisTemplate.keys(generateKey(namespace, "*"));
            redisTemplate.delete(keys);
            flag = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return flag;
    }
}
