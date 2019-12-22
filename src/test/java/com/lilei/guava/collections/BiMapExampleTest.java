package com.lilei.guava.collections;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @description: BiMap: 双向关联的数据结构
 * @author: Mr.Li
 * @date: Created in 2019/12/21 18:23
 * @version: 1.0
 * @modified By:
 *
 * BiMap:
 * A bimap (or "bidirectional map") is a map that preserves the uniqueness of its values as well as that of its keys.
 * This constraint enables bimaps to support an "inverse view", which is another bimap containing the same entries as this
 * bimap but with reversed keys and values.
 * 是根据value值确定唯一性的,所以BiMap中value不能重复,并且相同的key会被覆盖掉
 */
public class BiMapExampleTest {

    @Test
    public void testCreateAndPut() {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1", "2");
        biMap.put("1", "3");
        assertThat(biMap.containsKey("1"), is(true));
        assertThat(biMap.size(), equalTo(1));

        try {
            biMap.put("2", "3");
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 反转key和value互换
    @Test
    public void testBiMapInverse() {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1", "2");
        biMap.put("2", "3");
        biMap.put("3", "4");

        assertThat(biMap.containsKey("1"), is(true));
        assertThat(biMap.containsKey("2"), is(true));
        assertThat(biMap.containsKey("3"), is(true));
        assertThat(biMap.size(), equalTo(3));

        // key、value反转
        BiMap<String, String> inverseKey = biMap.inverse();
        assertThat(inverseKey.containsKey("2"), is(true));
        assertThat(inverseKey.containsKey("3"), is(true));
        assertThat(inverseKey.containsKey("4"), is(true));
        assertThat(inverseKey.size(), equalTo(3));
    }

    @Test
    public void testCreateAndForcePut() {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1", "2");
        assertThat(biMap.containsKey("1"), is(true));
        // 将value为2的键值对进行替换
        biMap.forcePut("2", "2");
        assertThat(biMap.containsKey("1"), is(false));
        assertThat(biMap.containsKey("2"), is(true));
    }
}
