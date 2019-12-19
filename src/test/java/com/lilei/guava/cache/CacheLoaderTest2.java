package com.lilei.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @description: Eviction by AccessTime, WriteTime and Four reference types
 * @author: Mr.Li
 * @date: Created in 2019/12/18 21:23
 * @version: 1.0
 * @modified By:
 */
public class CacheLoaderTest2 {

    // Access Time => Write/Update/Read
    @Test
    public void testEvictionByAccessTime() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                /**
                 * expireAfterAccess(long duration, TimeUnit unit):
                 * Specifies that each entry should be automatically removed from the cache once a fixed duration has
                 * elapsed after the entry's creation, the most recent replacement of its value, or its last access.
                 */
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .build(this.createCacheLoader());

        assertThat(cache.getUnchecked("Alex"), notNullValue());
        assertThat(cache.size(), equalTo(1L));

        TimeUnit.SECONDS.sleep(3);
        assertThat(cache.getIfPresent("Alex"), nullValue());

        assertThat(cache.getUnchecked("Guava"), notNullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getUnchecked("Guava"), notNullValue());
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getUnchecked("Guava"), notNullValue());
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getUnchecked("Guava"), notNullValue());
    }

    // Write Time -> Write/Update
    @Test
    public void testEvictionByWriteTime() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                /**
                 * expireAfterWrite(long duration, TimeUnit unit):
                 * Specifies that each entry should be automatically removed from the cache once a fixed duration
                 * has elapsed after the entry's creation, or the most recent replacement of its value.
                 */
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(this.createCacheLoader());

        assertThat(cache.getUnchecked("Alex"), notNullValue());
        assertThat(cache.size(), equalTo(1L));

        assertThat(cache.getUnchecked("Guava"), notNullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"), notNullValue());
        TimeUnit.MILLISECONDS.sleep(990);
        assertThat(cache.getIfPresent("Guava"), notNullValue());
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"), nullValue());

    }

    /**
     * Strong/Soft/Weak/Phantom Reference
     */
    @Test
    public void testWeakKey() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                // [ by default, strong references are used ]
                // weakKeys(): Specifies that each key stored in the cache should be wrapped in a WeakReference.
                .weakKeys()
                // weakValues(): Specifies that each value stored in the cache should be wrapped in a WeakReference.
                .weakValues()
                .build(this.createCacheLoader());

        assertThat(cache.getUnchecked("Alex"), notNullValue());
        assertThat(cache.getUnchecked("Guava"), notNullValue());

        // Weak Reference: 在GC的时候进行回收.
        System.gc();

        TimeUnit.MILLISECONDS.sleep(100);
        assertThat(cache.getIfPresent("Alex"), nullValue());
    }

    @Test
    public void testSoftKey() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                // [ by default, strong references are used ]
                // softValues(): Specifies that each value stored in the cache should be wrapped in a SoftReference.
                .softValues()
                .build(this.createCacheLoader());

        // Soft Reference: 快要发生OOM[堆内存快满了]的时候,去尝试回收在JVM当中的所有Soft Reference.
        int i = 0;
        for (; ; ) {
            // 	put(K key, V value): Associates value with key in this cache.
            cache.put("Alex" + i, new Employee("Alex" + i, "Alex" + i, "Alex" + i));
            System.out.println("The Employee [" + (i++) + "] is store into cache.");
            TimeUnit.MILLISECONDS.sleep(600);
        }
    }

    private CacheLoader<String, Employee> createCacheLoader() {
        // from(Function<K,V> function): Returns a cache loader that uses function to load keys, without supporting either reloading or bulk loading.
        return CacheLoader.from(name -> new Employee(name, name, name));

        // return new CacheLoader<>() {
        //     @Override public Employee load(String name) throws Exception {
        //         return new Employee(name, name + "Dept", name + "ID");
        //     }
        // };

    }
}
