package com.lilei.guava.utilities;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class PreconditionsTest {

    @Test(expected = NullPointerException.class)
    public void testCheckNotNull(){
        final List<String> list = null;
        //断言传进来的参数是不是null,如果是null会抛出异常NullPointerException
        Preconditions.checkNotNull(list);
    }


    @Test
    public void testCheckNotNullWithMessage() {
        try {
            final List<String> list = null;
            //断言传进来的参数是不是null,如果是null会输出我们自定义的Message
            Preconditions.checkNotNull(list,"The list should not be null");
        } catch (Exception exception) {
            // java.lang.NullPointerException: The list should not be null
            System.out.println(exception);
            System.out.println("---------------------------------");
            // is an instance of java.lang.NullPointerException
            System.out.println(is(instanceOf(NullPointerException.class)));

            /**
             * 断言exception为NullPointerException的实例
             * 如下的方式已经被"删除"了,无法使用,只能使用它的非简化版模式
             * assertThat(exception,is(NullPointerException.class));
             */
            assertThat(exception,is(instanceOf(NullPointerException.class)));

            //断言exception的Message是不是我们自定义的
            assertThat(exception.getMessage(), equalTo("The list should not be null"));
        }
    }

    //Format Message
    @Test
    public void testCheckNotNullWithFormatMessage() {
        try {
            final List<String> list = null;
            //断言传进来的参数是不是null,如果是null会输出我们自定义的Message
            Preconditions.checkNotNull(list,"The list should not be null and the size must be %s", 2);
        } catch (Exception exception) {
            // java.lang.NullPointerException: The list should not be null
            System.out.println(exception);
            System.out.println("---------------------------------");
            // is an instance of java.lang.NullPointerException
            System.out.println(is(instanceOf(NullPointerException.class)));

            /**
             * 断言exception为NullPointerException的实例
             * 如下的方式已经被"删除"了,无法使用,只能使用它的非简化版模式
             * assertThat(exception,is(NullPointerException.class));
             */
            assertThat(exception,is(instanceOf(NullPointerException.class)));

            //断言exception的Message是不是我们自定义的
            assertThat(exception.getMessage(), equalTo("The list should not be null and the size must be 2"));
        }
    }

    @Test
    public void testCheckArguments(){
        final String type = "A";
        try {
            /**
             * checkArgument(): Ensures the truth of an expression involving one or more parameters to the calling method.
             *                  确保涉及调用方法的一个或多个参数的表达式的真实性
             */
            Preconditions.checkArgument(type.equals("B"));
        } catch (Exception exception){
            System.out.println(exception);
            assertThat(exception,is(instanceOf(IllegalArgumentException.class)));
        }
    }

    @Test
    public void testCheckState(){
        try {
            final String state = "A";
            //checkStateEnsures(): the truth of an expression involving the state of the calling instance, but not involving any parameters to the calling method.
            //确保涉及调用实例状态的表达式的真实性,但不涉及调用方法的任何参数
            Preconditions.checkState(state.equals("B"),"The state is illegal.");
            //fail(): Fails a test with the given message.
            fail("should not process to here.");
        } catch (Exception exception) {
            System.out.println(exception);
            assertThat(exception,is(instanceOf(IllegalStateException.class)));
        }
    }

    @Test
    public void testCheckIndex(){
        try {
            //of(): Returns the empty immutable list.
            List<String> list = ImmutableList.of();
            Preconditions.checkElementIndex(10,list.size());
        } catch (Exception exception) {
            System.out.println(exception);
            assertThat(exception,is(instanceOf(IndexOutOfBoundsException.class)));
        }
    }

    //Java8中
    @Test(expected = NullPointerException.class)
    public void testByObjects(){
        //requireNonNull(): Checks that the specified object reference is not null
        //检查指定的对象引用是否为空
        Objects.requireNonNull(null);
    }

    @Test(expected = AssertionError.class)
    public void testAssert(){
        List<String> list = null;
        /**
         * 语法: assert [boolean表达式];
         *       如果[boolean表达式]为true,则程序继续执行
         *       如果为false,则程序抛出AssertionError,并终止执行
         */
        assert list != null;
    }

    @Test
    public void testAssertWithMessage(){
        try {
            List<String> list = null;
            /**
             * 语法: assert[boolean表达式 : 错误表达式(日志)]
             *       如果[boolean表达式]为true,则程序继续执行
             *       如果为false,则程序抛出java.lang.AssertionError,输出: 错误表达式(日志)
             *
             */
            assert list != null : "The list should not be null.";
        } catch (Error error) {     //这里不要写错了,AssertionError继承的是Error
            System.out.println(error);
            assertThat(error,is(instanceOf(AssertionError.class)));
            assertThat(error.getMessage(),equalTo("The list should not be null."));
        }
    }
}
