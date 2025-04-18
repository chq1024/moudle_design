package com.beikei.design.core;

import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;

import java.nio.ByteBuffer;

public class MyRedisCodec implements RedisCodec<String,Object> {
    @Override
    public String decodeKey(ByteBuffer bytes) {
        return StringCodec.UTF8.decodeKey(bytes);
    }

    @Override
    public Object decodeValue(ByteBuffer bytes) {
        return StringCodec.UTF8.decodeValue(bytes);
    }

    @Override
    public ByteBuffer encodeKey(String key) {
        return StringCodec.UTF8.encodeKey(key);
    }

    @Override
    public ByteBuffer encodeValue(Object value) {
        return StringCodec.UTF8.encodeValue((String) value);
    }
}
