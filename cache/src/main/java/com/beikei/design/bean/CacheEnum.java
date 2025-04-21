package com.beikei.design.bean;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public enum CacheEnum {

    HELLO("hello",String.class,60L),
    HELLO_LIST("helloList", List.class,60L),
    HELLO_MAP("helloMap",Map.class,60L),
    ;
    private final String key;
    private final Class<?> clazz;
    private final Long expireTime;

    CacheEnum(String key, Class<?> clazz,Long expireTime) {
        this.key = key;
        this.clazz = clazz;
        this.expireTime = expireTime;
    }

    public static CacheEnum match(String key) {
        CacheEnum cacheEnum = Arrays.stream(values()).filter(r -> r.getKey().equals(key)).findAny().orElse(null);
        if (cacheEnum == null) {
            throw new RuntimeException("未配置Key的枚举类");
        }
        return cacheEnum;
    }
}
