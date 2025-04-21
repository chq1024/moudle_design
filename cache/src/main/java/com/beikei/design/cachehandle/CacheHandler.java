package com.beikei.design.cachehandle;

public interface CacheHandler<K, V> {
    V get(K key);
    V put(K key,V value,Long expire);

    default V put(K key, V value) {
        return put(key,value,-1L);
    }

}
