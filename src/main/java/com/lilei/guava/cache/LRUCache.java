package com.lilei.guava.cache;

/**
 * @description: 模拟LRU算法接口
 * @author: Mr.Li
 * @date: Created in 2019/12/18 12:11
 * @version: 1.0
 * @modified By:
 */
public interface LRUCache<K, V> {

    public void put(K key, V value);

    public V get(K key);

    public void remove(K key);

    int size();

    void clear();

    int limit();
}
