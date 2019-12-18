package com.lilei.guava.cache;

/**
 * @description: 模拟LRU的测试类
 * @author: Mr.Li
 * @date: Created in 2019/12/18 14:00
 * @version: 1.0
 * @modified By:
 */
public class LinkedListLRUCacheExample {

    public static void main(String[] args) {

        LinkedListLRUCache<String, String> cache = new LinkedListLRUCache<String, String>(3);

        cache.put("1", "1");
        cache.put("2", "2");
        System.out.println(cache);
        // 使用一次cache中的数据
        System.out.println(cache.get("1"));
        System.out.println(cache);

        cache.put("3", "3");
        System.out.println(cache);
        // ("1", "1")被淘汰了
        cache.put("4", "4");
        System.out.println(cache);

        // 使用一次cache中的数据
        System.out.println(cache.get("2"));
        System.out.println(cache);
    }
}
