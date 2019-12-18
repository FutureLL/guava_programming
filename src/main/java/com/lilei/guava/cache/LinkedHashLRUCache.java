package com.lilei.guava.cache;

import com.google.common.base.Preconditions;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description: LinkedHashMap 的方式实现Cache
 * @author: Mr.Li
 * @date: Created in 2019/12/18 12:14
 * @version: 1.0
 * @modified By:
 */

/* This class is not the thread-safe class. */
public class LinkedHashLRUCache<K, V> implements LRUCache<K, V> {

    // 定义一个内部类,相当于重写了LinkedHashMap
    private static class InternalLRUCache<K, V> extends LinkedHashMap<K, V> {

        final private int limit;

        public InternalLRUCache(int limit) {
            super(16, 0.75f, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > limit;
        }
    }

    private final int limit;

    private final InternalLRUCache<K, V> internalLRUCache;

    public LinkedHashLRUCache(int limit) {

        Preconditions.checkArgument(limit > 0, "The limit big that zero.");
        this.limit = limit;
        this.internalLRUCache = new InternalLRUCache<>(limit);
    }

    @Override
    public void put(K key, V value) {
        this.internalLRUCache.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.internalLRUCache.get(key);
    }

    @Override
    public void remove(K key) {
        this.internalLRUCache.remove(key);
    }

    @Override
    public int size() {
        return this.internalLRUCache.size();
    }

    @Override
    public void clear() {
        this.internalLRUCache.clear();
    }

    @Override
    public int limit() {
        return this.internalLRUCache.limit;
    }

    @Override
    public String toString() {
        return internalLRUCache.toString();
    }
}
