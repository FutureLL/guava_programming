package com.lilei.guava.eventbus.test;

import com.lilei.guava.eventbus.internal.MySubscribe;

/**
 * @description:
 * @author: Alex Wang
 * @date: Created in 2019/12/14 12:22
 * @version: 1.0
 * @modified By: Mr.Li
 */
public class MySimpleListener
{

    @MySubscribe
    public void test1(String x)
    {
        System.out.println("MySimpleListener===test1==" + x);
    }

    @MySubscribe(topic = "alex-topic")
    public void test2(Integer x)
    {
        System.out.println("MySimpleListener===test2==" + x);
    }
}
