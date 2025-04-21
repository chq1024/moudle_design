package com.beikei.design.cachehandle;

import io.lettuce.core.api.sync.RedisCommands;

public class StringCacheHandler implements CacheHandler<String, Object>{
    private final RedisCommands<String,Object> commands;
    protected StringCacheHandler(RedisCommands<String, Object> commands) {
        this.commands = commands;
    }

    @Override
    public Object get(String key) {
        return commands.get(key);
    }

    @Override
    public Object put(String key, Object value,Long expire) {
        if (expire == -1) {
            return commands.set(key, value);
        }
        return commands.setex(key,expire,value);
    }
}
