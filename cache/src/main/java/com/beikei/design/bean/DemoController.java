package com.beikei.design.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
            return UUID.randomUUID().toString();
        });
        return val == null ? "" : val.toString();
    }
}
