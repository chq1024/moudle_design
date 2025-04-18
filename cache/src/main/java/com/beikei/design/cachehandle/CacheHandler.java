package com.beikei.design.cachehandle;

public interface CacheHandler<K, V> {
    V get(K key);

    V put(K key, V value);
}
