package com.beikei.design.core;

import io.lettuce.core.StatefulRedisConnectionImpl;
import io.lettuce.core.TrackingArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.support.caching.CacheAccessor;
import io.lettuce.core.support.caching.CacheFrontend;
import io.lettuce.core.support.caching.RedisCache;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class MyClientCache implements CacheFrontend<String, Object> {
    private final CacheAccessor<String, Object> cacheAccessor;
    private final RedisCache<String, Object> redisCache;
    private final List<Consumer<String>> invalidationListeners = new CopyOnWriteArrayList<>();

    private MyClientCache(CacheAccessor<String, Object> cacheAccessor, RedisCache<String, Object> redisCache) {
        this.cacheAccessor = cacheAccessor;
        this.redisCache = redisCache;
    }

    public static CacheFrontend<String, Object> enable(CacheAccessor<String, Object> cacheAccessor, StatefulRedisConnection<String, Object> connection, TrackingArgs tracking) {
        connection.sync().clientTracking(tracking);
        return create(cacheAccessor, connection);
    }

    public static CacheFrontend<String, Object> create(CacheAccessor<String, Object> cacheAccessor, StatefulRedisConnection<String, Object> connection) {
        StatefulRedisConnectionImpl<String, Object> connectionImpl = (StatefulRedisConnectionImpl) connection;
        RedisCodec<String, Object> codec = connectionImpl.getCodec();
        RedisCache<String, Object> redisCache = new MyRedisCache(connection, codec);
        return create(cacheAccessor, redisCache);
    }

    private static CacheFrontend<String, Object> create(CacheAccessor<String, Object> cacheAccessor, RedisCache<String, Object> redisCache) {
        MyClientCache cacheFrontend = new MyClientCache(cacheAccessor, redisCache);
        cacheFrontend.addInvalidationListener(cacheAccessor::evict);
        redisCache.addInvalidationListener(cacheFrontend::notifyInvalidate);
        return cacheFrontend;
    }

    public void addInvalidationListener(java.util.function.Consumer<String> invalidationListener) {
        invalidationListeners.add(invalidationListener);
    }

    private void notifyInvalidate(String key) {
        for (java.util.function.Consumer<String> invalidationListener : invalidationListeners) {
            invalidationListener.accept(key);
        }
    }

    @Override
    public void close() {
        redisCache.close();
    }


    @Override
    public Object get(String key) {
        Object value = cacheAccessor.get(key);
        if (value == null) {
            value = redisCache.get(key);
            if (value != null) {
                cacheAccessor.put(key, value);
            }
        }

        return value;
    }

    @Override
    public Object get(String key, Callable<Object> valueLoader) {
        Object value = cacheAccessor.get(key);
        if (value == null) {
            value = redisCache.get(key);
            if (value == null) {
                try {
                    value = valueLoader.call();
                } catch (Exception e) {
                    throw new ValueRetrievalException(String.format("Value loader %s failed with an exception for key %s", valueLoader, key), e);
                }
                if (value == null) {
                    throw new ValueRetrievalException(String.format("Value loader %s returned a null value for key %s", valueLoader, key));
                }
                redisCache.put(key, value);
                // register interest in key
                redisCache.get(key);
            }
            cacheAccessor.put(key, value);
        }

        return value;
    }

}
