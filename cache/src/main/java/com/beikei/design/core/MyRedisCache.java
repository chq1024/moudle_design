package com.beikei.design.core;

import com.beikei.design.bean.CacheEnum;
import com.beikei.design.cachehandle.CacheHandler;
import com.beikei.design.cachehandle.CacheHandlerFactory;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.support.caching.RedisCache;

import java.util.List;

class MyRedisCache implements RedisCache<String, Object> {

    private final StatefulRedisConnection<String, Object> connection;

    private final RedisCodec<String, Object> codec;

    public MyRedisCache(StatefulRedisConnection<String, Object> connection, RedisCodec<String, Object> codec) {
        this.connection = connection;
        this.codec = codec;
    }

    @Override
    public Object get(String key) {
        CacheHandler<String, Object> cacheHandler = matchHandlerByKey(key);
        return cacheHandler.get(key);
    }

    @Override
    public void put(String key, Object value) {
        CacheEnum cacheEnum = CacheEnum.match(key);
        CacheHandler<String, Object> cacheHandler = matchHandlerByEnum(cacheEnum);
        cacheHandler.put(key,value,cacheEnum.getExpireTime());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addInvalidationListener(java.util.function.Consumer<? super String> listener) {
        connection.addListener(message -> {
            if (message.getType().equals("invalidate")) {
                List<Object> content = message.getContent(codec::decodeKey);
                List<String> keys = (List<String>) content.get(1);
                keys.forEach(listener);
            }
        });
    }

    @Override
    public void close() {
        connection.close();
    }

    private CacheHandler<String,Object> matchHandlerByKey(String key) {
        CacheEnum cacheEnum = CacheEnum.match(key);
        return matchHandlerByEnum(cacheEnum);
    }

    private CacheHandler<String,Object> matchHandlerByEnum(CacheEnum cacheEnum) {
        RedisCommands<String, Object> commands = connection.sync();
        return CacheHandlerFactory.getHandler(cacheEnum.getClazz(), commands);
    }

}
