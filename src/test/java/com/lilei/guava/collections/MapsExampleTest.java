package com.lilei.guava.collections;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @description: Collections Maps
 * @author: Mr.Li
 * @date: Created in 2019/12/21 16:15
 * @version: 1.0
 * @modified By:
 * <p>
 * Maps:
 * Static utility methods pertaining to Map instances (including instances of SortedMap, BiMap, etc.).
 */
public class MapsExampleTest {

    @Test
    public void testCreate() {
        ArrayList<String> valueList = Lists.newArrayList("1", "2", "3");
        // uniqueIndex(Iterator<V> values, Function<? super V,K> keyFunction):
        // Returns a map with the given values, indexed by keys derived from those values.
        ImmutableMap<String, String> map = Maps.uniqueIndex(valueList, value -> value + "_key");
        // {1_key=1, 2_key=2, 3_key=3}
        System.out.println(map);

        // asMap(NavigableSet<K> set, Function<? super K,V> function):
        // Returns a view of the navigable set as a map, mapping keys from the set according to the specified function.
        Map<String, String> map2 = Maps.asMap(Sets.newHashSet("1", "2", "3"), key -> key + "_value");
        // {1=1_value, 2=2_value, 3=3_value}
        System.out.println(map2);
    }

    @Test
    public void testTransform() {
        Map<String, String> map = Maps.asMap(Sets.newHashSet("1", "2", "3"), key -> key + "_value");
        // transformValues(Map<K,V1> fromMap, Function<? super V1,V2> function):
        // Returns a view of a map where each value is transformed by a function.
        Map<String, String> newMap = Maps.transformValues(map, value -> value + "_transform");
        System.out.println(newMap);
        assertThat(newMap.containsValue("1_value_transform"), equalTo(true));
    }

    @Test
    public void testFilter() {
        Map<String, String> map = Maps.asMap(Sets.newHashSet("1", "2", "3"), key -> key + "_value");
        // filterKeys(Map<K,V> unfiltered, Predicate<? super K> keyPredicate):
        // Returns a map containing the mappings in unfiltered whose keys satisfy a predicate.
        Map<String, String> newMap = Maps.filterKeys(map, key -> Lists.newArrayList("1", "2").contains(key));
        System.out.println(newMap);
        assertThat(newMap.containsKey("3"), equalTo(false));
    }
}
