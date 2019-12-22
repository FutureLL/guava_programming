package com.lilei.guava.collections;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.NavigableMap;
import java.util.TreeMap;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @description: Collections Range
 * @author: Mr.Li
 * @date: Created in 2019/12/21 19:53
 * @version: 1.0
 * @modified By:
 * <p>
 * Range:
 * A range (or "interval") defines the boundaries around a contiguous span of values of some Comparable type.
 * Range 表示一个间隔或一个序列,它被用于获取一组数字/串在一个特定范围之内,可比较类型的区间API,包括连续和离散类型。
 * Range 定义了连续跨度的范围边界,这个连续跨度是一个可以比较的类型(Comparable type),比如1到100之间的整型数据。
 * 在数学里面的范围是有边界和无边界之分的.同样,在Guava中也有这个说法,如果这个范围是有边界的,那么这个范围又可以分为包括,
 * 开集(不包括端点)和闭集(包括端点)。如果是无解的可以用 +∞ 表示。
 */
public class RangeExampleTest {

    /**
     * {x|a<=x<=b}
     */
    @Test
    public void testClosedRange() {
        // closed(C lower, C upper): Returns a range that contains all values greater than or equal to lower and less than or equal to upper.
        Range<Integer> closedRange = Range.closed(0, 9);
        System.out.println(closedRange);    // [0..9]

        assertThat(closedRange.contains(5), is(true));
        // lowerEndpoint(): Returns the lower endpoint of this range.
        assertThat(closedRange.lowerEndpoint(), equalTo(0));
        // upperEndpoint(): Returns the upper endpoint of this range.
        assertThat(closedRange.upperEndpoint(), equalTo(9));
    }

    /**
     * {x|a<x<b}
     */
    @Test
    public void testOpenRange() {
        // open(C lower, C upper): Returns a range that contains all values strictly greater than lower and strictly less than upper.
        Range<Integer> openRange = Range.open(0, 9);
        System.out.println(openRange);      // (0..9)

        assertThat(openRange.contains(5), is(true));

        assertThat(openRange.lowerEndpoint(), equalTo(0));
        assertThat(openRange.upperEndpoint(), equalTo(9));
        assertThat(openRange.contains(0), is(false));
        assertThat(openRange.contains(9), is(false));
    }

    /**
     * {x|a<x<=b}
     */
    @Test
    public void testOpenClosedRange() {
        // openClosed(C lower, C upper): Returns a range that contains all values strictly greater than lower and less than or equal to upper.
        Range<Integer> openClosedRange = Range.openClosed(0, 9);
        System.out.println(openClosedRange);        // (0..9]

        assertThat(openClosedRange.contains(5), is(true));

        assertThat(openClosedRange.lowerEndpoint(), equalTo(0));
        assertThat(openClosedRange.upperEndpoint(), equalTo(9));
        assertThat(openClosedRange.contains(0), is(false));
        assertThat(openClosedRange.contains(9), is(true));
    }


    /**
     * {x|a<=x<b}
     */
    @Test
    public void testClosedOpenRange() {
        // closedOpen(C lower, C upper): Returns a range that contains all values greater than or equal to lower and strictly less than upper.
        Range<Integer> closedOpen = Range.closedOpen(0, 9);
        System.out.println(closedOpen);     // [0..9)

        assertThat(closedOpen.contains(5), is(true));

        assertThat(closedOpen.lowerEndpoint(), equalTo(0));
        assertThat(closedOpen.upperEndpoint(), equalTo(9));
        assertThat(closedOpen.contains(0), is(true));
        assertThat(closedOpen.contains(9), is(false));
    }

    /**
     * {x|x>a}  /  {x|x<a}
     */
    @Test
    public void testGreaterThanAndLessThan() {
        // greaterThan(C endpoint): Returns a range that contains all values strictly greater than endpoint.
        Range<Integer> greaterRange = Range.greaterThan(10);
        System.out.println(greaterRange);      // (10..+∞)
        assertThat(greaterRange.contains(10), is(false));
        assertThat(greaterRange.contains(Integer.MAX_VALUE), is(true));

        // lessThan(C endpoint): Returns a range that contains all values strictly less than endpoint.
        Range<Integer> lessRange = Range.lessThan(10);
        System.out.println(lessRange);         // (-∞..10)
        assertThat(lessRange.contains(10), is(false));
        assertThat(lessRange.contains(Integer.MIN_VALUE), is(true));
    }

    /**
     * {x|x>=a}  /  {x|x<=a}  /  {x|-∞<x<+∞}
     */
    @Test
    public void testOtherMethod() {
        // atLeast(C endpoint): Returns a range that contains all values greater than or equal to endpoint.
        Range<Integer> atLeastRange = Range.atLeast(2);
        System.out.println(atLeastRange);       // [2..+∞)

        System.out.println("-------------------");

        assertThat(atLeastRange.contains(2), is(true));
        System.out.println(Range.lessThan(10));     // (-∞..10)
        System.out.println(Range.atMost(5));        // (-∞..5]
        System.out.println(Range.all());            // (-∞..+∞)

        // downTo(C endpoint, BoundType boundType):
        // Returns a range from the given endpoint, which may be either inclusive (closed) or exclusive (open), with no upper bound.
        // 下降到10
        System.out.println(Range.downTo(10, BoundType.CLOSED));     // [10..+∞)

        // upTo(C endpoint, BoundType boundType):
        // Returns a range with no lower bound up to the given endpoint, which may be either inclusive (closed) or exclusive (open).
        // 上升到10
        System.out.println(Range.upTo(10, BoundType.CLOSED));       // (-∞..10]
    }

    @Test
    public void testMapRange() {
        TreeMap<String, Integer> treeMap = Maps.newTreeMap();

        treeMap.put("Scala", 1);
        treeMap.put("Guava", 2);
        treeMap.put("MySQL", 3);
        treeMap.put("Kafka", 4);
        System.out.println(treeMap);

        System.out.println("--------------------");

        NavigableMap<String, Integer> result = Maps.subMap(treeMap, Range.openClosed("Guava", "MySQL"));
        System.out.println(result);
    }

    @Test
    public void testRangeMap() {
        RangeMap<Integer, String> gradeScale = TreeRangeMap.create();
        gradeScale.put(Range.closed(0, 60), "E");
        gradeScale.put(Range.closed(61, 70), "D");
        gradeScale.put(Range.closed(71, 80), "C");
        gradeScale.put(Range.closed(81, 90), "B");
        gradeScale.put(Range.closed(91, 100), "A");

        assertThat(gradeScale.get(77), equalTo("C"));
    }
}
