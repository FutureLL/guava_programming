package com.lilei.guava.cache;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @description: LinkedList + HashMap的方式实现Cache
 * @author: Mr.Li
 * @date: Created in 2019/12/18 13:14
 * @version: 1.0
 * @modified By:
 */
public class LinkedListLRUCache<K, V> implements LRUCache<K, V> {

    private final int limit;

    // 链表控制顺序
    private final LinkedList<K> keys = new LinkedList<>();

    // Map用来存值
    private final HashMap<K, V> cache = new HashMap<>();

    public LinkedListLRUCache(int limit) {
        this.limit = limit;
    }

    @Override
    public void put(K key, V value) {

        Preconditions.checkNotNull(key, "The key cannot be null");
        Preconditions.checkNotNull(value, "The value cannot be null");
        if (keys.size() >= limit) {
            // 删除存留时间最长的元素
            K oldestKey = keys.removeFirst();
            cache.remove(oldestKey);
        }
        keys.addLast(key);
        cache.put(key, value);
    }

    @Override
    public V get(K key) {

        boolean exist = keys.remove(key);
        // 不存在
        if (!exist) {
            return null;
        }
        // 放到最新的节点位置
        keys.addLast(key);
        return cache.get(key);
    }

    @Override
    public void remove(K key) {
        boolean exist = keys.remove(key);
        if (exist) {
            cache.remove(key);
        }
    }

    @Override
    public int size() {
        return this.keys.size();
    }

    @Override
    public void clear() {
        this.keys.clear();
        this.cache.clear();
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        for (K key : keys) {
            builder.append(key).append("=").append(cache.get(key)).append(";");
        }
        return builder.toString();
    }
}
