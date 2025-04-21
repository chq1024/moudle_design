package com.beikei.design.cachehandle;

import io.lettuce.core.api.sync.RedisCommands;

public class CacheHandlerFactory {

    public static CacheHandler<String, Object> getHandler(Class<?> clazz, RedisCommands<String, Object> commands) {
        return switch (clazz.getTypeName().toUpperCase()) {
            case "STRING" -> new StringCacheHandler(commands);
            case "MAP" -> new MapCacheHandler(commands);
            case "LIST" -> new ListCacheHandler(commands);
            default -> throw new RuntimeException("未找到匹配的Handler");
        };
    }
}
