package com.lilei.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @description: CacheBuilder 和 CacheLoader
 * @author: Mr.Li
 * @date: Created in 2019/12/18 16:36
 * @version: 1.0
 * @modified By:
 */
public class CacheLoaderTest {

    private boolean isTrue = false;

    @Test
    public void testBasic() throws ExecutionException, InterruptedException {
        // 创建Cache实例
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                /**
                 * maximumSize(long maximumSize): Specifies the maximum number of entries the cache may contain.
                 * 也是最大连接数
                 */
                .maximumSize(10)
                /**
                 * expireAfterAccess(): Specifies that each entry should be automatically removed from the cache once a fixed
                 * duration has elapsed after the entry's creation, the most recent replacement of its value, or its last
                 * access.
                 * 简单说就是存活时间
                 */
                .expireAfterAccess(30, TimeUnit.MILLISECONDS)
                // build(): Builds a cache
                .build(createCacheLoader());


        /**
         * 任何缓存都应该提供 获取缓存-如果没有-则计算[get-if-absent-compute] 这一基础原子语义,具体含义如下:
         *      1、从缓存中取
         *      2、缓存中存在该数据,直接返回
         *      3、缓存中不存在该数据,从数据源中取
         *      4、数据源中存在该数据,放入缓存,并返回
         *      5、数据源中不存在该数据,返回空
         *
         * 提取当前选择为变量(Extract Variable):Ctrl+Alt+V 【选中多个相同的变量进行更改】
         * 提取当前选择为方法(Extract Method):Ctrl + Alt + M【将方法提取出来成为公共方法】
         */

        // get(K key): Returns the value associated with key in this cache, first loading that value if necessary.
        Employee employee = cache.get("Alex");
        assertThat(employee, notNullValue());
        // 第一次拿是从DB中
        assertLoadFromDBThenReset();

        employee = cache.get("Alex");
        assertThat(employee, notNullValue());
        // 第二次拿是从Cache中
        assertLoadFromCache();

        // 由于这里设置了睡眠,导致cache过期
        TimeUnit.MILLISECONDS.sleep(31);
        // 所以再一次拿是从DB中
        employee = cache.get("Alex");
        assertThat(employee, notNullValue());
        assertLoadFromDBThenReset();
    }

    // 逐出策略
    @Test
    public void testEvictionBySize() {
        // 提取当前选择为方法(Extract Method):Ctrl + Alt + M
        CacheLoader<String, Employee> cacheLoader = createCacheLoader();
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .build(cacheLoader);

        // getUnchecked(K key): Returns the value associated with key in this cache, first loading that value if necessary.
        cache.getUnchecked("Alex");
        // 第一次拿是从DB中,因为key不同,所以第一次都是从DB中拿
        assertLoadFromDBThenReset();

        cache.getUnchecked("Jack");
        assertLoadFromDBThenReset();

        cache.getUnchecked("Gavin");
        assertLoadFromDBThenReset();

        // 断言cache大小
        assertThat(cache.size(), equalTo(3L));

        // 再来一个的时候,那么Alex会被溢出
        cache.getUnchecked("Susan");
        assertLoadFromDBThenReset();
        // getIfPresent(Object key): Returns the value associated with key in this cache, or null if there is no cached value for key.
        assertThat(cache.getIfPresent("Alex"), equalTo(null));
        assertThat(cache.getIfPresent("Susan"), notNullValue());

        // 断言cache大小
        assertThat(cache.size(), equalTo(3L));

        // 所以再一次使用Alex的时候缓存中没有,根本拿不到,所以会报错:java.lang.AssertionError:
        // cache.getUnchecked("Alex");
        // assertLoadFromCache();
    }

    @Test
    public void testEvictionByWeight() throws ExecutionException {

        Weigher<String, Employee> weigher = new Weigher<String, Employee>() {
            @Override
            public int weigh(String key, Employee employee) {
                return employee.getName().length() * 3;
            }
        };

        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                // maximumWeight(long maximumWeight): Specifies the maximum weight of entries the cache may contain.
                .maximumWeight(45)
                // concurrencyLevel(int concurrencyLevel): Guides the allowed concurrency among update operations.
                .concurrencyLevel(1)
                // weigher(Weigher weigher): Specifies the weigher to use in determining the weight of entries.
                .weigher(weigher)
                .build(createCacheLoader());

        cache.getUnchecked("Gavin");
        assertLoadFromDBThenReset();

        cache.getUnchecked("Kevin");
        assertLoadFromDBThenReset();

        cache.getUnchecked("Allen");
        assertLoadFromDBThenReset();

        // 这里只有两个,Gavin溢出了,因为设定了缓存的权重,权重不能大于45,所以有一个溢出
        assertThat(cache.size(), equalTo(3L));
        // 这里使用过一次Gavin所以Gavin已经不是最老的了,Kevin是最老的
        assertThat(cache.getIfPresent("Gavin"), notNullValue());

        cache.getUnchecked("Jason");
        assertLoadFromDBThenReset();

        assertThat(cache.size(), equalTo(3L));
        // 添加了新的,那么最老的Kevin就被溢出了
        assertThat(cache.getIfPresent("Kevin"), nullValue());
    }

    private CacheLoader<String, Employee> createCacheLoader() {
        return new CacheLoader<>() {
            @Override
            public Employee load(String name) throws Exception {
                return findEmployeeByName(name, name + "Dept", name + "ID");
            }
        };
    }

    private void assertLoadFromDBThenReset() {
        assertThat(true, equalTo(isTrue));
        this.isTrue = false;
    }

    private void assertLoadFromCache() {
        assertThat(false, equalTo(isTrue));
    }

    private Employee findEmployeeByName(final String name, final String dept, String empID) {
        // System.out.print("The employee is: " + name + ", department is: " + dept + ", employeeID is: " + empID);
        // System.out.println(",That data is loaded from the DB.");
        isTrue = true;
        return new Employee(name, dept, empID);
    }
}
