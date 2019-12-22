package com.lilei.guava.collections;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @description: Collections Lists
 * @author: Mr.Li
 * @date: Created in 2019/12/21 14:03
 * @version: 1.0
 * @modified By:
 * <p>
 * Lists:
 * Static utility methods pertaining to List instances. Also see this class's counterparts Sets, Maps and Queues.
 */
public class ListsExampleTest {

    @Test
    public void testCartesianProduct() {
        // cartesianProduct(): Returns every possible list that can be formed by choosing one element from each of the given lists in order.
        // the "n-ary Cartesian product" of the lists.
        List<List<String>> result = Lists.cartesianProduct(
                // newArrayList(E... elements): Creates a mutable ArrayList instance containing the given elements.
                Lists.newArrayList("1", "2"),
                Lists.newArrayList("A", "B")
        );
        System.out.println(result);
    }

    @Test
    public void testTransform() {
        ArrayList<String> sourceList = Lists.newArrayList("Scala", "Guava", "Lists");
        // transform(List fromList, Function function): Returns a list that applies function to each element of fromList.
        List<String> transform = Lists.transform(sourceList, str -> str.toUpperCase());
        transform.forEach(System.out::println);
    }

    @Test
    public void testNewArrayListWithCapacity() {
        // newArrayListWithCapacity(int initialArraySize): Creates an ArrayList instance backed by an array with the specified initial size.
        ArrayList<String> result = Lists.newArrayListWithCapacity(10);
        result.add("x");
        result.add("y");
        result.add("z");
        System.out.println(result);
    }

    @Test
    public void testNewArrayListWithExpectedSize() {
        // This method will soon be deprecated.
        // newArrayListWithExpectedSize(int estimatedSize): Creates an ArrayList instance to hold estimatedSize elements, plus an unspecified amount of padding.
        ArrayList<String> result = Lists.newArrayListWithExpectedSize(2);
        result.add("x");
        result.add("y");
        result.add("z");
        System.out.println(result);
    }

    @Test
    public void testReverse() {
        ArrayList<String> list = Lists.newArrayList("1", "2", "3");
        assertThat(Joiner.on(",").join(list), equalTo("1,2,3"));

        // reverse(List<T> list): Returns a reversed view of the specified list.
        List<String> result = Lists.reverse(list);
        assertThat(Joiner.on(",").join(result), equalTo("3,2,1"));
    }

    @Test
    public void testPartition() {
        ArrayList<String> list = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7");
        // 分区
        // partition(List list, int size): Returns consecutive sublists of a list, each of the same size (the final list may be smaller).
        List<List<String>> result = Lists.partition(list, 3);
        System.out.println(result.get(0));
        System.out.println(result.get(1));
        System.out.println(result.get(2));
        /**
         * 分了三个区
         *      [1, 2, 3]
         *      [4, 5, 6]
         *      [7]
         */
    }

    @Test
    public void testNewCopyOnWriteArrayList() {
        ArrayList<String> list = Lists.newArrayList("1", "2", "3", "4");
        // newCopyOnWriteArrayList(Iterable elements): Creates a CopyOnWriteArrayList instance containing the given elements.
        // 它是个线程安全且读操作无锁的ArrayList,写操作则通过创建底层数组的新副本来实现,是一种读写分离的并发策略
        // 适合读多写少的操作
        CopyOnWriteArrayList<String> result = Lists.newCopyOnWriteArrayList(list);
        System.out.println(result);
    }
}
