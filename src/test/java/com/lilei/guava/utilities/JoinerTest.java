package com.lilei.guava.utilities;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JoinerTest {

    private final List<String> stringList = Arrays.asList(
            "Google","Guava","Java","Scala","Kafka"
    );

    private final List<String> stringListWithNullValue = Arrays.asList(
            "Google","Guava","Java","Scala",null
    );

    private final String targetFileName = "E:\\Google Guava\\guava-joiner.txt";

    private final Map<String,String> stringMap = of("Hello","Guava","Java","Scala");
    private final String targetFileNameToMap = "E:\\Google Guava\\guava-joiner-map.txt";
    /**
     * Joiner: 定义一个集合之间的连接
     * on(): 通过什么分隔符进行连接
     * join(): 要连接的集合
     * assertThat():【断言】,用来判断通过Joiner将集合变成我们想要的方式,也就是真实值,将真实值和期望值进行比对,相同不会输出什么,不同会报错
     *      第一个参数表示真实值,第二个参数表示期望值
     */
    @Test
    public void testJoinOnJoin() {
        String result = Joiner.on("#").join(stringList);
        System.out.println(result);
        assertThat(result,equalTo("Google#Guava#Java#Scala#Kafka"));
    }

    @Test(expected = NullPointerException.class)
    public void testJoinOnJoinWithNullValue() {
        /**
         * String result = Joiner.on("#").join(stringListWithNullValue);
         * 如果是这样会报错 java.lang.NullPointerException 空指针异常
         */
        String result = Joiner.on("#").join(stringListWithNullValue);
        assertThat(result,equalTo("Google#Guava#Java#Scala"));
    }

    @Test
    public void testJoinOnJoinWithNullValueButSkip() {
        /**
         * skipNulls(): 跳过集合中的null,这样就不会出现空指针异常了
         */
        String result = Joiner.on("#").skipNulls().join(stringListWithNullValue);
        System.out.println(result);
        assertThat(result,equalTo("Google#Guava#Java#Scala"));
    }

    @Test
    public void testJoin_On_Join_WithNullValueBut_UseDefaultValue() {
        /**
         * useForNull(): null值全部被代替
         */
        String result = Joiner.on("#").useForNull("DEFAULT").join(stringListWithNullValue);
        System.out.println(result);
        assertThat(result,equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    @Test
    public void testJoin_On_Append_To_StringBuilder(){
        final StringBuilder builder = new StringBuilder();
        /**
         * appendTo(): Appends the string representation of each of parts,using the previously configured separator between each, to appendable.
         *             使用预先配置的分隔符,将各个部分的字符串表示形式追加到可追加项
         */
        StringBuilder resultBuilder = Joiner.on("#").useForNull("DEFAULT").appendTo(builder, stringListWithNullValue);
        assertThat(resultBuilder,sameInstance(builder));
        assertThat(resultBuilder.toString(),equalTo("Google#Guava#Java#Scala#DEFAULT"));
        assertThat(builder.toString(),equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    @Test
    public void testJoin_On_Append_To_Writer(){

        try(FileWriter writer = new FileWriter(new File(targetFileName))){
            Joiner.on("#").useForNull("DEFAULT").appendTo(writer, stringListWithNullValue);
            assertThat(Files.isFile().test(new File(targetFileName)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error.");
        }
    }

    //使用Java8实现一个Joiner,去掉null
    @Test
    public void testJoiningByStreamSkipNullValues(){
        String result = stringListWithNullValue
                .stream()
                .filter(item -> item != null && !item.isEmpty())
                .collect(joining("#"));
        System.out.println(result);
        assertThat(result,equalTo("Google#Guava#Java#Scala"));
    }

    //使用Java8实现一个Joiner,null被替换掉
    @Test
    public void testJoiningByStreamWithDefaultValue(){
        String result = stringListWithNullValue
                .stream()
                .map(this::defaultValue)
                .collect(joining("#"));
        System.out.println(result);
        assertThat(result,equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }
    public String defaultValue(final String item){
        //return (item == null || item.isEmpty()) ? "DEFAULT" : item;
        return item == null ? "DEFAULT" : item;
    }

    //Map
    @Test
    public void testJoinOnWithMap(){
        String joinMap = Joiner.on('#').withKeyValueSeparator("=").join(stringMap);
        System.out.println(joinMap);
        assertThat(joinMap,equalTo("Hello=Guava#Java=Scala"));
    }

    //Map
    @Test
    public void testJoinOnWithMapToAppendable(){
        try(FileWriter writer = new FileWriter(new File(targetFileNameToMap))){
            Joiner.on("#").withKeyValueSeparator("=").appendTo(writer, stringMap);
            assertThat(Files.isFile().test(new File(targetFileNameToMap)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error.");
        }
    }
}