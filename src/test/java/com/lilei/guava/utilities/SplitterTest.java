package com.lilei.guava.utilities;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/8 16:00
 * @version: 1.0
 * @modified By:
 */
public class SplitterTest {

    @Test
    public void testSplitOnSplit(){
        List<String> result = Splitter.on("|").splitToList("hello|world");
        //这个List不是Null
        assertThat(result,notNullValue());
        //List大小
        assertThat(result.size(),equalTo(2));
        //第一个元素值
        assertThat(result.get(0),equalTo("hello"));
        //第二个元素值
        assertThat(result.get(1),equalTo("world"));
    }

    @Test
    public void testSplit_On_Split_OmitEmpty(){
        List<String> result = Splitter.on("|").splitToList("hello|world|||");
        assertThat(result,notNullValue());
        /**
         * 这种情况下result的大小应该是5,而不是2
         * 所以这条代码会报错: assertThat(result.size(),equalTo(2));
         *      java.lang.AssertionError:
         *      Expected: <2>
         *           but: was <5>
         */
        assertThat(result.size(),equalTo(5));

        System.out.println("------------------------------");

        //omitEmptyStrings(): 忽略空的字符串
        result = Splitter.on("|").omitEmptyStrings().splitToList("hello|world|||");
        assertThat(result,notNullValue());
        assertThat(result.size(),equalTo(2));
    }

    @Test
    public void testSplit_On_Split_OmitEmpty_TrimResult(){
        List<String> result = Splitter.on("|").omitEmptyStrings().splitToList(" hello | world |||");
        assertThat(result,notNullValue());
        assertThat(result.size(),equalTo(2));
        assertThat(result.get(0),equalTo(" hello "));
        assertThat(result.get(1),equalTo(" world "));

        System.out.println("------------------------------");

        //trimResults(): 去除字符串前后的空格
        result = Splitter.on("|").trimResults().omitEmptyStrings().splitToList(" hello | world | | | ");
        assertThat(result,notNullValue());
        assertThat(result.size(),equalTo(2));
        assertThat(result.get(0),equalTo("hello"));
        assertThat(result.get(1),equalTo("world"));
    }

    //将字符串按照固定个数进行分割
    @Test
    public void testSplitFixLength(){
        //fixedLength(): 按照固定的个数进行分割字符串
        List<String> result = Splitter.fixedLength(4).splitToList("aaaabbbbccccdddd");
        assertThat(result,notNullValue());
        assertThat(result.size(),equalTo(4));
        assertThat(result.get(0),equalTo("aaaa"));
        assertThat(result.get(3),equalTo("dddd"));
    }

    @Test
    public void testSplitOnSplitLimit(){
        //limit(): 以分隔符分割,限制分割的最大值
        List<String> result = Splitter.on("#").limit(3).splitToList("hello#world#java#google#scala");
        assertThat(result,notNullValue());
        assertThat(result.size(),equalTo(3));
        assertThat(result.get(0),equalTo("hello"));
        assertThat(result.get(1),equalTo("world"));
        assertThat(result.get(2),equalTo("java#google#scala"));
    }

    @Test
    public void testSplitOnPatternString(){
        //onPattern(): 传入正则表达式的字符串
        List<String> result = Splitter.onPattern("\\|").trimResults().omitEmptyStrings().splitToList("hello | world|||");
        assertThat(result,notNullValue());
        assertThat(result.size(),equalTo(2));
        assertThat(result.get(0),equalTo("hello"));
        assertThat(result.get(1),equalTo("world"));
    }

    @Test
    public void testSplitOnPattern(){
        //on(): 传入一个正则的Pattern
        List<String> result = Splitter.on(Pattern.compile("\\|")).trimResults().omitEmptyStrings().splitToList("hello | world|||");
        assertThat(result,notNullValue());
        assertThat(result.size(),equalTo(2));
        assertThat(result.get(0),equalTo("hello"));
        assertThat(result.get(1),equalTo("world"));
    }

    @Test
    public void testSplitOnSplitToMap(){
        //on(): 传入一个正则的Pattern
        Map<String,String> resultMap = Splitter.on(Pattern.compile("\\|"))
                //去除元素前后空格
                .trimResults()
                //去除分隔符之间为空的元素
                .omitEmptyStrings()
                //设置Map集合key/value之间的分隔符
                .withKeyValueSeparator("=")
                //分割的字符串
                .split("hello=HELLO | world=WORLD |||");
        assertThat(resultMap,notNullValue());
        assertThat(resultMap.size(),equalTo(2));
        assertThat(resultMap.containsKey("hello"),equalTo(true));
        assertThat(resultMap.get("world"),equalTo("WORLD"));
    }
}
