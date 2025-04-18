/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.beikei.design.core;

import com.beikei.design.bean.CacheEnum;
import com.beikei.design.cachehandle.CacheHandler;
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
        RedisCommands<String, Object> commands = connection.sync();
        CacheHandler<String,Object> cacheHandler = CacheEnum.match(key,commands);
        return cacheHandler.get(key);
    }

    @Override
    public void put(String key, Object value) {
        RedisCommands<String, Object> commands = connection.sync();
        CacheHandler<String,Object> cacheHandler = CacheEnum.match(key,commands);
        cacheHandler.put(key,value);
    }

    @Override
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

}
