package com.lilei.guava.collections;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @description: Collections FluentIterable
 * @author: Mr.Li
 * @date: Created in 2019/12/21 10:29
 * @version: 1.0
 * @modified By:
 *
 * FluentIterable:
 * A discouraged (but not deprecated) precursor to Java's superior Stream library.
 */
public class FluentIterableExampleTest {

    private FluentIterable<String> build() {
        ArrayList<String> list = Lists.newArrayList("Alex", "Wang", "Guava", "Scala");
        // from(Iterable iterable): Returns a fluent iterable that wraps iterable, or iterable itself if it is already a FluentIterable.
        return FluentIterable.from(list);
    }

    @Test
    public void testFilter() {
        FluentIterable<String> fit = build();
        // size(): Returns the number of elements in this fluent iterable.
        assertThat(fit.size(), equalTo(4));

        // filter(Predicate<? super E> predicate): Returns the elements from this fluent iterable that satisfy a predicate.
        // 从迭代器中返回满足谓词的元素
        FluentIterable<String> result = fit.filter(str ->
                str != null && str.length() > 4
        );
        assertThat(result.size(), equalTo(2));
    }

    @Test
    public void testAppend() {
        ArrayList<String> append = Lists.newArrayList("APPEND");
        FluentIterable<String> fit = build();
        assertThat(fit.size(), equalTo(4));

        // append(): Returns a fluent iterable whose iterators traverse first the elements of this fluent iterable, followed by those of other.
        // 相当于追加
        fit = fit.append(append);
        assertThat(fit.size(), equalTo(5));
        // contains(@Nullable Object target): Returns true if this fluent iterable contains any object for which equals(target) is true.
        assertThat(fit.contains("APPEND"), equalTo(true));

        fit = fit.append("APPEND2");
        assertThat(fit.size(), equalTo(6));
        assertThat(fit.contains("APPEND2"), equalTo(true));
    }

    @Test
    public void testMach() {
        FluentIterable<String> fit = build();
        // allMatch(Predicate<? super E> predicate): Returns true if every element in this fluent iterable satisfies the predicate.
        boolean result = fit.allMatch(str ->
                str != null && str.length() >= 4
        );
        assertThat(result, equalTo(true));

        // anyMatch(Predicate<? super E> predicate): Returns true if any element in this fluent iterable satisfies the predicate.
        result = fit.anyMatch(str ->
                str != null && str.length() == 5
        );
        assertThat(result, equalTo(true));

        Optional<String> optional = fit.firstMatch(str ->
                str != null && str.length() == 5
        );
        /**
         * Guava首先使用更有含义的Optional对象来表示一个可能为空值的对象,它的意义在于用这样一个难忘的名字来迫使你处理值为空的情况
         * Optional: An immutable object that may contain a non-null reference to another object.
         */
        // isPresent(): Returns true if this holder contains a (non-null) instance.
        assertThat(optional.isPresent(), equalTo(true));
        // get(): Returns the contained instance, which must be present.
        assertThat(optional.get(), equalTo("Guava"));
    }

    @Test
    public void testFirst$Last() {
        FluentIterable<String> fit = build();
        // first(): Returns an Optional containing the first element in this fluent iterable.
        Optional<String> first = fit.first();
        assertThat(first.isPresent(), equalTo(true));
        assertThat(first.get(), equalTo("Alex"));

        Optional<String> last = fit.last();
        assertThat(first.isPresent(), equalTo(true));
        // last(): Returns an Optional containing the last element in this fluent iterable.
        assertThat(last.get(), equalTo("Scala"));
    }

    @Test
    public void testLimit() {
        FluentIterable<String> fit = build();
        // limit(int maxSize): Creates a fluent iterable with the first size elements of this fluent iterable.
        FluentIterable<String> limit = fit.limit(3);
        System.out.println(limit);
        assertThat(limit.contains("Scala"), equalTo(false));
        assertThat(limit.size(), equalTo(3));

        limit = fit.limit(10);
        System.out.println(limit);
        assertThat(limit.contains("Scala"), equalTo(true));
        assertThat(limit.size(), equalTo(4));
    }

    @Test
    public void testCopyIn() {
        FluentIterable<String> fit = build();
        ArrayList<String> list = Lists.newArrayList("Java");
        // copyInto(C collection): Copies all the elements from this fluent iterable to collection.
        ArrayList<String> result = fit.copyInto(list);

        assertThat(result.size(), equalTo(5));
        assertThat(result.contains("Scala"), equalTo(true));
        assertThat(result.contains("Java"), equalTo(true));
    }

    @Test
    public void testCycle() {
        FluentIterable<String> fit = build();
        // cycle(): Returns a fluent iterable whose Iterator cycles indefinitely over the elements of this fluent iterable.
        // 无线循环的一个FluentIterable
        FluentIterable<String> cycle = fit.cycle().limit(20);
        cycle.forEach(System.out::println);
    }

    @Test
    public void testTransform() {
        FluentIterable<String> fit = build();
        // transform(Function function): Returns a fluent iterable that applies function to each element of this fluent iterable.
        FluentIterable<Integer> transform = fit.transform(str -> str.length());
        transform.forEach(System.out::println);
    }

    @Test
    public void testTransformAndConcat() {
        FluentIterable<String> fit = build();
        ArrayList<Integer> list = Lists.newArrayList(1,2);
        FluentIterable<Integer> result = fit
                /**
                 * transformAndConcat(Function<? super E,? extends Iterable<? extends T>> function):
                 * Applies function to each element of this fluent iterable and returns a fluent iterable with the concatenated combination of results.
                 */
                .transformAndConcat(str -> list);
        result.forEach(System.out::println);
    }

    /**
     * A ----> API ----> B(Service) ----> 分别查出两种类型的数据
     * 1,2
     */
    @Test
    public void testTransformAndConcatInAction() {
        ArrayList<Integer> cTypes = Lists.newArrayList(1, 2);
        FluentIterable.from(cTypes)
                .transformAndConcat(type -> search(type))
                .forEach(System.out::println);
    }

    private List<Customer> search(int type) {
        if (type == 1) {
            return Lists.newArrayList(new Customer(type, "Alex"), new Customer(type, "Tina"));
        } else {
            return Lists.newArrayList(new Customer(type, "Wang"), new Customer(type, "Wen"), new Customer(type, "Jun"));
        }
    }

    class Customer {
        final int type;
        final String name;

        Customer(int type, String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "type = " + type +
                    ", name = '" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void testJoin() {
        FluentIterable<String> fit = FluentIterable
                .from(Lists.newArrayList("Alex", "Wang", "Guava", "Scala"));

        // join(Joiner joiner): Returns a String containing all of the elements of this fluent iterable joined with joiner.
        assertThat(fit.join(Joiner.on(",")), equalTo("Alex,Wang,Guava,Scala"));
    }
}
