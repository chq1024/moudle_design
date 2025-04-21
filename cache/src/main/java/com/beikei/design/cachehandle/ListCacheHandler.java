package com.beikei.design.cachehandle;

import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;

public class ListCacheHandler implements CacheHandler<String,Object>{

    private final RedisCommands<String,Object> redisCommands;
    public ListCacheHandler(RedisCommands<String,Object> redisCommands) {
        this.redisCommands = redisCommands;
    }
    @Override
    public Object get(String key) {
        List<Object> valueList = redisCommands.lrange(key, 0, -1);
        if (valueList == null || valueList.isEmpty()) {
            return null;
        }
        return valueList;
    }

    @Override
    public Object put(String key, Object value) {
        return redisCommands.lpush(key,value);
    }
}
