package com.glory.gloryCacheRedis.cache;

import java.util.List;

public interface CacheCallback<T> {

    T getObject() throws Exception;

    List<T> getObjectList() throws Exception;

}