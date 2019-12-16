package com.lilei.guava.utilities;

import com.google.common.base.CharMatcher;
        import com.google.common.base.Charsets;
        import com.google.common.base.Strings;
        import org.junit.Test;

        import java.nio.charset.Charset;

        import static org.hamcrest.core.IsEqual.equalTo;
        import static org.hamcrest.core.IsNull.nullValue;
        import static org.junit.Assert.assertThat;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/9 20:13
 * @version: 1.0
 * @modified By:
 */
public class StringsTest {

    //字符串操作
    @Test
    public void teststringsmethod(){
        //emptyToNull(): 给一个空的字符串,变成一个null
        //nullValue(): 值为null
        assertThat(Strings.emptyToNull(""),nullValue());
        //nullToEmpty(): 给定一个字符串为null,变成一个空的字符串
        assertThat(Strings.nullToEmpty(null),equalTo(""));
        //如果不为空,那么返回原值
        assertThat(Strings.emptyToNull("hello"),equalTo("hello"));
        //如果不为null,那么返回原值
        assertThat(Strings.nullToEmpty("hello"),equalTo("hello"));
        //commonPrefix(): 拥有公共的前缀,并返回公共前缀
        assertThat(Strings.commonPrefix("Hello","Hit"),equalTo("H"));
        //公共的前缀为空
        assertThat(Strings.commonPrefix("Hello","Java"),equalTo(""));
        //commonPrefix(): 拥有公共的后缀,并返回公共后缀
        assertThat(Strings.commonSuffix("Java","Google Java"),equalTo("Java"));
        //公共的后缀为空
        assertThat(Strings.commonSuffix("JavaScript","Google Java"),equalTo(""));
        //repeat(): Returns a string consisting of a specific number of concatenated copies of an input string.
        assertThat(Strings.repeat("Alex",3),equalTo("AlexAlexAlex"));
        //isNullOrEmpty(): Returns true if the given string is null or is the empty string.
        assertThat(Strings.isNullOrEmpty(null) || Strings.isNullOrEmpty(""),equalTo(true));
        //padStart(): Returns a string, of length at least minLength, consisting of string prepended with as many copies of padChar as are necessary to reach that length.
        assertThat(Strings.padStart("Alex",3,'H'),equalTo("Alex"));
        assertThat(Strings.padStart("Alex",5,'H'),equalTo("HAlex"));
        //Returns a string, of length at least minLength, consisting of string appended with as many copies of padChar as are necessary to reach that length.
        assertThat(Strings.padEnd("Alex",3,'H'),equalTo("Alex"));
        assertThat(Strings.padEnd("Alex",5,'H'),equalTo("AlexH"));
    }

    //字符集
    @Test
    public void testCharsets(){
        Charset charset = Charset.forName("UTF-8");
        assertThat(Charsets.UTF_8,equalTo(charset));
    }

    //字符匹配
    @Test
    public void testCharMatcher(){
        //javaDigit(): 是否为数字类型
        assertThat(CharMatcher.javaDigit().matches('5'),equalTo(true));
        assertThat(CharMatcher.javaDigit().matches('x'),equalTo(false));
        //一个字符串中有多少个指定字符
        assertThat(CharMatcher.is('A').countIn("Alex Sharing the Google Guava to Us"),equalTo(1));
        //去掉空格,并且将连续不间断的空格形成一组,每一组都会被replacement所表示的字符代替
        assertThat(CharMatcher.breakingWhitespace().collapseFrom("      hello   Guava    ",'*'),equalTo("*hello*Guava*"));
        //删除给定字符串removeFrom()中的 数字javaDigit() 或者or() 空格whitespace() 并返回
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).removeFrom("hello 234 world"),equalTo("helloworld"));
        //保留给定字符串retainFrom()中的 数字javaDigit() 或者or() 空格whitespace() 并返回
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).retainFrom("hello 234 world"),equalTo(" 234 "));
    }
}