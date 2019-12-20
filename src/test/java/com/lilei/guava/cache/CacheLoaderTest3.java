package com.lilei.guava.cache;

import com.google.common.base.Optional;
import com.google.common.cache.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @description: NullValue、NullValueUseOptional、Refresh、PreLoad and RemovedNotification
 * @author: Mr.Li
 * @date: Created in 2019/12/20 8:39
 * @version: 1.0
 * @modified By:
 */
public class CacheLoaderTest3 {

    @Test
    public void testLoadNullValue() {
        CacheLoader<String, Employee> cacheLoader = CacheLoader
                .from(name -> name.equals("null") ? null : new Employee(name, name, name));

        LoadingCache<String, Employee> loadingCache = CacheBuilder.newBuilder().build(cacheLoader);

        Employee alex = loadingCache.getUnchecked("Alex");
        assertThat(alex, notNullValue());
        assertThat(alex.getName(), equalTo("Alex"));

        // 证明CacheLoader中key不能为null
        try {
            // 如果忘记了静态方法,那么找到CoreMatchers类中的所有方法都是静态方法
            assertThat(loadingCache.getUnchecked("null"), nullValue());
            fail("should not process to here.");
        } catch (Exception exception) {
            assertThat(exception instanceof CacheLoader.InvalidCacheLoadException, equalTo(true));
        }
    }

    // 通过optional的方式可以为null
    @Test
    public void testLoadNullValueUseOptional() {
        CacheLoader<String, Optional<Employee>> loader = new CacheLoader<String, Optional<Employee>>() {
            @Override
            public Optional<Employee> load(String name) throws Exception {
                if (name.equals("null")) {
                    return Optional.fromNullable(null);
                } else {
                    return Optional.fromNullable(new Employee(name, name, name));
                }
            }
        };

        LoadingCache<String, Optional<Employee>> cache = CacheBuilder.newBuilder().build(loader);
        assertThat(cache.getUnchecked("Alex").get(), notNullValue());
        assertThat(cache.getUnchecked("null").orNull(), nullValue());

        // 如果为null给一个默认值
        Employee def = cache.getUnchecked("null").or(new Employee("default", "default", "default"));
        assertThat(def.getName().length(), equalTo(7));
    }


    /**
     * 不要拿过期数据
     * Thread1 => Cache Service1 method1 Alex x
     * Thread2 => Cache Service2 method2 Alex y
     */
    @Test
    public void testCacheRefresh() throws InterruptedException {
        // AtomicInteger: An int value that may be updated atomically.
        AtomicInteger counter = new AtomicInteger(0);
        CacheLoader<String, Long> cacheLoader = CacheLoader
                .from(name -> {
                    // 下边给的条件需要拿两次
                    System.out.println("每次拿都会打印这句话");
                    // incrementAndGet(): Atomically increments by one the current value.
                    counter.incrementAndGet();
                    return System.currentTimeMillis();
                });

        LoadingCache<String, Long> cache = CacheBuilder.newBuilder()
                /**
                 * refreshAfterWrite(long duration, TimeUnit unit):
                 * 指定活动项在其创建后或其值的最近一次替换之后经过固定的持续时间后,有资格进行自动刷新。
                 * 也就是说它只会记住这个时间如果下次拿的时候如果超过这个时间那么不会再去Cache中去拿,会重新帮你再拿一个
                 * Specifies that active entries are eligible for automatic refresh once a fixed duration has elapsed
                 * after the entry's creation, or the most recent replacement of its value.
                 */
                .refreshAfterWrite(2, TimeUnit.SECONDS)
                .build(cacheLoader);

        Long result1 = cache.getUnchecked("Alex");
        assertThat(counter.get(), equalTo(1));
        TimeUnit.SECONDS.sleep(3);
        Long result2 = cache.getUnchecked("Alex");
        Long result3 = cache.getUnchecked("Alex");
        assertThat(counter.get(), equalTo(2));
        assertThat(result1.longValue() != result2.longValue(), equalTo(true));
        assertThat(result2.longValue() == result3.longValue(), equalTo(true));
    }

    // 在启动Cache的时候就将数据进行加载
    // 但是在这里有个漏洞,无论我的Value是什么都会转换成大写,这就存在了漏洞
    @Test
    public void testCachePreLoad() {
        // 这里相当于定义了一个规则: Value必须是Key的大写
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader);

        Map<String, String> preData = new HashMap<String, String>() {
            {
                put("alex", "ALEX");
                put("hello", "hello");
            }
        };

        // putAll(Map<? extends K,? extends V> m): Copies all of the mappings from the specified map to the cache.
        cache.putAll(preData);
        assertThat(cache.size(), equalTo(2L));
        assertThat(cache.getUnchecked("alex"), equalTo("ALEX"));
        assertThat(cache.getUnchecked("hello"), equalTo("hello"));
    }

    // 通知方式
    @Test
    public void testCacheRemovedNotification() {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        RemovalListener<String, String> listener = notification -> {
            // wasEvicted(): Returns true if there was an automatic removal due to eviction.
            if (notification.wasEvicted()) {
                RemovalCause cause = notification.getCause();
                assertThat(cause, is(RemovalCause.SIZE));
                // 被逐出的key
                System.out.println(notification.getKey());
                assertThat(notification.getKey(), equalTo("Alex"));
            }
        };

        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                /**
                 * removalListener(RemovalListener listener):
                 * Specifies a listener instance that caches should notify each time an entry is removed for any reason.
                 */
                .removalListener(listener)
                .build(loader);

        cache.getUnchecked("Alex");
        cache.getUnchecked("Jerry");
        cache.getUnchecked("Jack");
        cache.getUnchecked("Jenny");
    }
}
