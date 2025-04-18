package com.beikei.design.cachehandle;

import io.lettuce.core.api.sync.RedisCommands;

public class ListCacheHandler implements CacheHandler<String,Object>{

    private final RedisCommands<String,Object> redisCommands;
    public ListCacheHandler(RedisCommands<String,Object> redisCommands) {
        this.redisCommands = redisCommands;
    }
    @Override
    public Object get(String key) {
        return redisCommands.lrange(key,0,-1);
    }

    @Override
    public Object put(String key, Object value) {
        return redisCommands.lpush(key,value);
    }
}
