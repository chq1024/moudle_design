package com.beikei.design.cachehandle;

import io.lettuce.core.api.sync.RedisCommands;

import java.util.Map;

public class MapCacheHandler implements CacheHandler<String,Object>{
    private final RedisCommands<String,Object> commands;
    protected MapCacheHandler(RedisCommands<String, Object> commands) {
        this.commands = commands;
    }

    @Override
    public Object get(String key) {
        Map<String, Object> valueMp = commands.hgetall(key);
        if (valueMp == null || valueMp.isEmpty()) {
            return null;
        }
        return valueMp;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object put(String key, Object value, Long expire) {
        String callback = commands.hmset(key, (Map<String, Object>) value);
        if (expire != -1) {
            commands.expire(key,expire);
        }
        return callback;
    }
}
