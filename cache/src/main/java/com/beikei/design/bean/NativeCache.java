package com.beikei.design.bean;

import com.beikei.design.core.MyClientCacheFrontend;
import com.beikei.design.core.MyRedisCodec;
import com.beikei.design.core.NativeCacheAccessor;
import com.github.benmanes.caffeine.cache.Cache;
import io.lettuce.core.RedisClient;
import io.lettuce.core.TrackingArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.support.caching.CacheFrontend;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
public class NativeCache {
    private final RedisClient redisClient;
    private final Cache<String, Object> cache;
    // 操作
    private CacheFrontend<String, Object> frontend;
    StatefulRedisConnection<String, Object> redisConnection;

    public NativeCache(RedisClient redisClient, Cache<String, Object> cache) {
        this.redisClient = redisClient;
        this.cache = cache;
    }

    public Object get(String key) {
        return frontend.get(key);
    }

    public Object get(String key, Callable<Object> valueLoader) {
        return frontend.get(key, valueLoader);
    }

    public Cache<String,Object> cache() {
        return cache;
    }

    public void check() {
        if (redisConnection != null) {
            if (redisConnection.isOpen()) {
                return;
            }
        }
        try {
            redisConnection = redisClient.connect(new MyRedisCodec());
            this.frontend = MyClientCacheFrontend.enable(new NativeCacheAccessor(cache), redisConnection, TrackingArgs.Builder.enabled());
            log.info("contention had been reconnect....");
        } catch (Exception e) {
            log.error("connection had been disconnected,waiting reconnect....");
        }
    }
}
