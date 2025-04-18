package com.beikei.design.bean;

import com.beikei.design.core.MyClientSideCaching;
import com.beikei.design.core.MyRedisCodec;
import com.github.benmanes.caffeine.cache.Cache;
import io.lettuce.core.RedisClient;
import io.lettuce.core.TrackingArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.support.caching.CacheFrontend;
import lombok.extern.slf4j.Slf4j;

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
            this.frontend = MyClientSideCaching.enable(new CaffeineCacheAccessor(cache), redisConnection, TrackingArgs.Builder.enabled());
            log.info("contention had been reconnect....");
        } catch (Exception e) {
            log.error("connection had been disconnected,waiting reconnect....");
        }
    }
}
