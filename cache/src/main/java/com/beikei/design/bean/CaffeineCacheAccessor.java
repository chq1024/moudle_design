package com.beikei.design.bean;

import com.github.benmanes.caffeine.cache.Cache;
import io.lettuce.core.support.caching.CacheAccessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaffeineCacheAccessor implements CacheAccessor<String,Object> {

    private Cache<String,Object> cache;

    public CaffeineCacheAccessor(Cache<String,Object> cache) {
        this.cache = cache;
    }

    @Override
    public Object get(String key) {
        Object val = cache.getIfPresent(key);
        log.info("caffeine:get:{}:value:{}",key,val);
        return val;
    }

    @Override
    public void put(String key, Object value) {
        log.info("caffeine:put:{}",key);
        cache.put(key,value);
    }

    @Override
    public void evict(String key) {
        log.warn("caffeine:evict:{}",key);
        cache.invalidate(key);
    }
}
