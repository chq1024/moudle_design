package com.beikei.design.bean;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.lettuce.core.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


@Configuration
public class CacheConfiguration {

    @Bean
    public CommandLineRunner init(@Autowired NativeCache nativeCache) {
        return (args) -> {
            while (true) {
                nativeCache.check();
                Thread.sleep(2000);
            }
        };
    }

    @Bean
    @Qualifier("cache")
    public Cache<String,Object> cache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(5))
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
    }

    @Bean
    @Qualifier("redisClient")
    public RedisClient redisClient(@Autowired LettuceConnectionFactory connectionFactory) {
        return (RedisClient) connectionFactory.getNativeClient();
    }

    @Bean
    public NativeCache nativeCache(@Autowired @Qualifier("redisClient") RedisClient redisClient,@Autowired @Qualifier("cache") Cache<String,Object> cache) {
        return new NativeCache(redisClient,cache);
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(@Autowired LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}

