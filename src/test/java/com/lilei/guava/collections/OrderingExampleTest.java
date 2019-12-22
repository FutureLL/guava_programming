package com.lilei.guava.collections;

import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @description: Collections Ordering
 * @author: Mr.Li
 * @date: Created in 2019/12/22 12:16
 * @version: 1.0
 * @modified By:
 * <p>
 * Ordering:
 * A comparator, with additional methods to support common operations.
 * This is an "enriched" version of Comparator for pre-Java-8 users,
 * in the same sense that FluentIterable is an enriched Iterable for pre-Java-8 users.
 */
public class OrderingExampleTest {

    @Test
    public void testJDKOrder() {
        List<Integer> list = Arrays.asList(1, 5, 3, 8, 2);
        System.out.println(list);

        Collections.sort(list);
        System.out.println(list);
    }

    @Test(expected = NullPointerException.class)
    public void testJDKOrderIssue() {
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        System.out.println(list);

        Collections.sort(list);
        System.out.println(list);
    }

    @Test
    public void testOrderNaturalByNullFirst() {
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        // natural(): Returns a serializable ordering that uses the natural order of the values.
        // nullsFirst(): Returns an ordering that treats null as less than all other values and uses this to compare non-null values.
        Collections.sort(list, Ordering.natural().nullsFirst());
        System.out.println(list);
    }

    @Test
    public void testOrderNaturalByNullLast() {
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        // nullsLast(): Returns an ordering that treats null as greater than all other values and uses this ordering to compare non-null values.
        Collections.sort(list, Ordering.natural().nullsLast());
        System.out.println(list);
    }

    @Test
    public void testOrderNatural() {
        List<Integer> list = Arrays.asList(1, 5, 3, 8, 2);
        Collections.sort(list);
        // isOrdered(Iterable<? extends T> iterable):
        // Returns true if each element in iterable after the first is greater than or equal to the element that preceded it, according to this ordering.
        // 如果按照此顺序,在第一个元素之后的iterable中的每个元素都大于或等于它之前的元素,则返回true。
        assertThat(Ordering.natural().isOrdered(list), is(true));
    }

    @Test
    public void testOrderReverse() {
        List<Integer> list = Arrays.asList(1, 5, 3, 8, 2);
        // reverse(): Returns the reverse of this ordering; the Ordering equivalent to Collections.reverseOrder(Comparator).
        Collections.sort(list, Ordering.natural().reverse());
        System.out.println(list);
        // max(Iterable<E> iterable): Returns the greatest of the specified values according to this ordering.
        System.out.println(Ordering.natural().max(list));
        // min(Iterable<E> iterable): Returns the least of the specified values according to this ordering.
        System.out.println(Ordering.natural().min(list));
    }
}
