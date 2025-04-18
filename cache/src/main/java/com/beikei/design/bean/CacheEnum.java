package com.beikei.design.bean;

import com.beikei.design.cachehandle.CacheHandler;
import com.beikei.design.cachehandle.ListCacheHandler;
import com.beikei.design.cachehandle.MapCacheHandler;
import com.beikei.design.cachehandle.StringCacheHandler;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public enum CacheEnum {

    HELLO("hello",String.class),
    HELLO_LIST("helloList", List.class),
    HELLO_MAP("helloMap",Map.class),
    ;
    private String key;
    private Class<?> clazz;
    CacheEnum(String key, Class<?> clazz) {
        this.key = key;
        this.clazz = clazz;
    }

    public static CacheHandler<String, Object> match(String key, RedisCommands<String,Object> commands) {
        CacheEnum cacheEnum = Arrays.stream(values()).filter(r -> r.getKey().equals(key)).findAny().orElse(null);
        if (cacheEnum == null) {
            throw new RuntimeException("未配置Key的枚举类");
        }
        Class<?> clazz = cacheEnum.getClazz();
        if (clazz.equals(Map.class)) {
            return new MapCacheHandler(commands);
        } else if (clazz.equals(List.class)) {
            return new ListCacheHandler(commands);
        }
        return new StringCacheHandler(commands);
    }
}
