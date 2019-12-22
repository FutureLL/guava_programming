package com.lilei.guava.collections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @description: Collections Immutable
 * @author: Mr.Li
 * @date: Created in 2019/12/22 11:14
 * @version: 1.0
 * @modified By:
 * <p>
 * Immutable: 表示不可变的
 * ImmutableBiMap / ImmutableList / ImmutableMap / ImmutableSet / ImmutableTable ......
 */
public class ImmutableCollectionsTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testOf() {
        // of(E e1, E e2, E e3): Returns an immutable list containing the given elements, in order.
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
        assertThat(list, notNullValue());
        // 这里会报错: UnsupportedOperationException
        list.add(4);
    }

    @Test
    public void testCopy() {
        Integer[] array = {1, 2, 3, 4, 5};
        // copyOf(E[] elements): Returns an immutable list containing the given elements, in order.
        ImmutableList<Integer> listCopy = ImmutableList.copyOf(array);
        assertThat(listCopy, notNullValue());
        assertThat(listCopy.size(), equalTo(5));
    }

    @Test
    public void testBuilder() {
        // builder(): Returns a new builder
        ImmutableList<Integer> list = ImmutableList.<Integer>builder()
                .add(1)
                .add(2, 3, 4)
                .addAll(Arrays.asList(5, 6))
                .build();

        System.out.println(list);
    }

    @Test
    public void testImmutableMap() {
        ImmutableMap<String, String> ofMap = ImmutableMap
                .of("Java", "1.8")
                .of("Oracle", "12c", "MySQL", "7.0");

        System.out.println(ofMap);

        System.out.println("-------------------");

        // builder(): Returns a new builder.
        ImmutableMap<String, String> map = ImmutableMap.<String, String>builder()
                .put("Oracle", "12c")
                .put("Mysql", "7.0")
                .build();

        System.out.println(map);

        try {
            // 初始化好之后不允许put
            map.put("Scala", "2.3.0");
            fail();
        } catch (Exception e) {
            assertThat(e instanceof UnsupportedOperationException, equalTo(true));
        }
    }
}
