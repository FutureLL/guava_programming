package com.lilei.guava.collections;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @description: Collections Sets
 * @author: Mr.Li
 * @date: Created in 2019/12/21 15:28
 * @version: 1.0
 * @modified By:
 * <p>
 * Sets:
 * Static utility methods pertaining to Set instances. Also see this class's counterparts Lists, Maps and Queues.
 */
public class SetsExampleTest {

    @Test
    public void testCreate() {
        // newHashSet(): Creates a mutable, initially empty HashSet instance.
        HashSet<Integer> set1 = Sets.newHashSet();
        set1.add(1);
        set1.add(2);
        set1.add(3);
        assertThat(set1.size(), equalTo(3));

        // newHashSet(E... elements): Creates a mutable HashSet instance initially containing the given elements.
        HashSet<Integer> set2 = Sets.newHashSet(1, 2, 3);
        assertThat(set2.size(), equalTo(3));

        // newHashSet(Iterable<? extends E> elements): Creates a mutable HashSet instance containing the given elements.
        HashSet<Integer> set3 = Sets.newHashSet(Lists.newArrayList(1, 2, 3, 4));
        assertThat(set3.size(), equalTo(4));
    }

    @Test
    public void testCartesianProduct() {
        // cartesianProduct(Set<? extends B>... sets):
        // Returns every possible list that can be formed by choosing one element from each of the given sets in order.
        // the "n-ary Cartesian product" of the sets.
        Set<List<Integer>> set = Sets.cartesianProduct(
                Sets.newHashSet(1, 2),
                Sets.newHashSet(3, 4),
                Sets.newHashSet(5, 6)
        );
        System.out.println(set);
    }

    @Test
    public void testCombinations() {
        HashSet<Integer> set = Sets.newHashSet(1, 2, 3);
        // combinations(Set<E> set, int size): Returns the set of all subsets of set of size size.
        Set<Set<Integer>> combinations = Sets.combinations(set, 2);
        /**
         * 两两组合:
         *      [1, 2]
         *      [1, 3]
         *      [2, 3]
         */
        combinations.forEach(System.out::println);
    }

    @Test
    public void testDifference() {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> set2 = Sets.newHashSet(1, 4, 6);
        // difference(Set<E> set1, Set<?> set2): Returns an unmodifiable view of the difference of two sets.
        // set1 have, but set2 haven't ===> [2, 3]
        Sets.SetView<Integer> difference1 = Sets.difference(set1, set2);
        System.out.println(difference1);

        // set2 have, but set1 haven't ===> [4, 6]
        Sets.SetView<Integer> difference2 = Sets.difference(set2, set1);
        System.out.println(difference2);
    }

    @Test
    public void testIntersection() {
        HashSet<Integer> set1 = Sets.newHashSet(1, 4, 3);
        HashSet<Integer> set2 = Sets.newHashSet(1, 4, 6);
        // intersection(Set<E> set1, Set<?> set2): Returns an unmodifiable view of the intersection of two sets.
        Sets.SetView<Integer> intersection = Sets.intersection(set1, set2);
        // [1, 4]
        System.out.println(intersection);
    }

    @Test
    public void testUnionSection() {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> set2 = Sets.newHashSet(1, 4, 6);
        // union(Set<? extends E> set1, Set<? extends E> set2): Returns an unmodifiable view of the union of two sets.
        Sets.SetView<Integer> union = Sets.union(set1, set2);
        // [1, 2, 3, 4, 6]
        System.out.println(union);
    }

    @Test
    public void testFilter() {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        // filter(Set<E> unfiltered, Predicate predicate): Returns the elements of unfiltered that satisfy a predicate.
        Set<Integer> filter = Sets.filter(set1, index -> index > 2);
        System.out.println(filter);
    }
}
