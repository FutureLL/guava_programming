package com.lilei.guava.collections;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @description: Collection Multimap
 * @author: Mr.Li
 * @date: Created in 2019/12/21 17:21
 * @version: 1.0
 * @modified By:
 * <p>
 * Multimap:
 * Provides static methods acting on or generating a Multimap.
 * 一个key可以有多个value
 */
public class MultimapExampleTest {

    @Test
    public void testBasic() {

        HashMap<Object, Object> hashMap = Maps.newHashMap();
        hashMap.put("1","1");
        hashMap.put("1","2");
        // 重复的key就被替换掉了
        assertThat(hashMap.size(), equalTo(1));

        LinkedListMultimap<String, String> multipleMap = LinkedListMultimap.create();
        multipleMap.put("1", "1");
        multipleMap.put("1", "2");
        multipleMap.put("2", "2");

        assertThat(multipleMap.size(), equalTo(3));
        System.out.println(multipleMap.get("1"));

        System.out.println("------------------");

        multipleMap.forEach((key, value) ->
                System.out.println("key：" + key + ", value：" + value)
        );
    }

    @Test
    public void containsOccurrences(){
        Multiset<String> set1 = HashMultiset.create();
        set1.add("a", 2);
        set1.add("b");
        set1.add("b");

        Multiset<String> set2 = HashMultiset.create();
        set2.add("a", 5);

        Multiset<String> set3 = HashMultiset.create();
        set2.add("a", 1);
        set2.add("b", 1);

        System.out.println(set1);
        System.out.println(set2);

        System.out.println("------------------");

        System.out.println(set1.containsAll(set2));
        System.out.println(set2.containsAll(set1));
        System.out.println("------------------");
        System.out.println(set1.count("a"));
        System.out.println(set1.count("b"));
        System.out.println("------------------");
        // containsOccurrences(Multiset<?> superMultiset, Multiset<?> subMultiset):
        // Returns true if subMultiset.count(o) <= superMultiset.count(o) for all o.
        // ----> false
        System.out.println(Multisets.containsOccurrences(set1, set2));
        System.out.println(Multisets.containsOccurrences(set2, set1));
        System.out.println(Multisets.containsOccurrences(set1, set3));
        System.out.println("------------------");
        // remove(@Nullable Object element, int occurrences):
        // Removes a number of occurrences of the specified element from this multiset.
        System.out.println(set2.remove("a",2));
        System.out.println(set2.size());
        System.out.println("------------------");
        // removeAll(Collection<?> c):
        // Removes all of this collection's elements that are also contained in the specified collection.
        // 删除set2中包含的所有set1,跟数量没有关系,只跟元素有关
        System.out.println(set2.removeAll(set1));
        System.out.println(set2.size());
        System.out.println(set2.isEmpty());
    }

    @Test
    public void difference_sum_union(){
        Multiset<String> set1 = HashMultiset.create();
        set1.add("a", 3);
        set1.add("b", 2);
        set1.add("c", 1);

        Multiset<String> set2 = HashMultiset.create();
        set2.add("a");
        set2.add("c");
        set2.add("e");

        // 差集 [set1有,set2没有]
        Multiset<String> differenceSet = Multisets.difference(set1, set2);
        System.out.println(differenceSet);  // [a x 2, b x 2]

        // 合集 [set1和set2所有的元素]
        Multiset<String> sumSet = Multisets.sum(set1, set2);
        System.out.println(sumSet);         // [a x 4, b x 2, c x 2, e]

        // 并集 [各元素之间的并集,]
        Multiset<String> unionSet = Multisets.union(set1, set2);
        System.out.println(unionSet);       // [a x 3, b x 2, c, e]
    }
}
