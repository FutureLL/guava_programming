package com.lilei.guava.cache;

import com.google.common.cache.*;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @description: CacheStats and CacheSpec
 * @author: Mr.Li
 * @date: Created in 2019/12/20 12:46
 * @version: 1.0
 * @modified By:
 */
public class CacheLoaderTest4 {

    @Test
    public void testCacheStat() {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(5)
                // recordStats(): Enable the accumulation of CacheStats during the operation of the cache.
                .recordStats()
                .build(loader);

        assertCache(cache);
    }

    @Test
    public void testCacheSpec() {
        String spec = "maximumSize=5,recordStats";
        //parse(String cacheBuilderSpecification): Creates a CacheBuilderSpec from a string.
        CacheBuilderSpec builderSpec = CacheBuilderSpec.parse(spec);

        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.from(builderSpec).build(loader);

        assertCache(cache);
    }

    private void assertCache(LoadingCache<String, String> cache) {

        // ALEX
        assertThat(cache.getUnchecked("alex"), equalTo("ALEX"));

        // CacheStats: Statistics about the performance of a Cache. Instances of this class are immutable.
        CacheStats stats = cache.stats();
        // hashCode(): Returns a hash code value for the object.
        System.out.println(stats.hashCode());
        // hitCount(): Returns the number of times Cache lookup methods have returned a cached value.
        // 也称作命中次数
        assertThat(stats.hitCount(), equalTo(0L));
        // missCount(): Returns the number of times Cache lookup methods have returned an uncached (newly loaded) value, or null.
        // 也称作未命中次数
        assertThat(stats.missCount(), equalTo(1L));

        // 这一次拿就从Cache中去拿
        assertThat(cache.getUnchecked("alex"), equalTo("ALEX"));

        // 因为Stats不可变,它被final修饰了,产生一次就不会再被修改,所以这里需要一个新的cache.stats()
        // 好处: 多线程状态下,不可变对象,可以避免加锁的方式,解决资源共享的问题
        stats = cache.stats();
        System.out.println(stats.hashCode());
        assertThat(stats.hitCount(), equalTo(1L));
        assertThat(stats.missCount(), equalTo(1L));

        // missRate(): Returns the ratio of cache requests which were misses.
        // 命中率
        System.out.println(stats.missRate());
        // hitRate(): Returns the ratio of cache requests which were hits.
        // 未命中率
        System.out.println(stats.hitRate());
    }
}
