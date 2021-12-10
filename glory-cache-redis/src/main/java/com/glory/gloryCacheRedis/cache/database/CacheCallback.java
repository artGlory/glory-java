package com.glory.gloryCacheRedis.cache.database;

import java.util.List;

public interface CacheCallback<T> {

    T getObject() throws Exception;

    List<T> getObjectList() throws Exception;

}