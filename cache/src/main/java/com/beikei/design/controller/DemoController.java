package com.beikei.design.controller;

import com.beikei.design.bean.CacheEnum;
import com.beikei.design.bean.NativeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("")
public class DemoController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private NativeCache nativeCache;

    @GetMapping("/set")
    public void set(@RequestParam("k") String key) {
//        redisTemplate.opsForValue().set(key, UUID.randomUUID().toString());
//        redisTemplate.opsForList().leftPush(key,UUID.randomUUID().toString());
        redisTemplate.opsForHash().put(key,"A",UUID.randomUUID().toString());
        redisTemplate.opsForHash().put(key,"B",UUID.randomUUID().toString());
    }

    @GetMapping("/get")
    public String get(@RequestParam("k") String key) {
//        Object val = redisTemplate.opsForValue().get(key);
//        Object val = nativeCache.get(key);
        Object val = nativeCache.get(key,()->{
            CacheEnum cacheEnum = CacheEnum.match(key);
            Class<?> clazz = cacheEnum.getClazz();
            if (clazz.equals(String.class)) {
                return UUID.randomUUID().toString();
            } else if (clazz.equals(List.class)) {
                ArrayList<Object> arr = new ArrayList<>();
                arr.add(UUID.randomUUID().toString());
                arr.add(UUID.randomUUID().toString());
                return arr;
            } else if (clazz.equals(Map.class)) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("A",UUID.randomUUID().toString());
                map.put("B",UUID.randomUUID().toString());
                return map;
            } else {
                return null;
            }
        });
        return val == null ? "" : val.toString();
    }
}
