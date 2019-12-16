package com.lilei.guava.eventbus.test;

import com.lilei.guava.eventbus.internal.MyAsyncEventBus;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description:
 * @author: Alex Wang
 * @date: Created in 2019/12/14 12:22
 * @version: 1.0
 * @modified By: Mr.Li
 */
public class MyAsyncBusExample
{

    public static void main(String[] args)
    {
        MyAsyncEventBus eventBus = new MyAsyncEventBus((ThreadPoolExecutor) Executors.newFixedThreadPool(4));
        eventBus.register(new MySimpleListener());
        eventBus.register(new MySimpleListener2());
        eventBus.post(123131, "alex-topic");
        eventBus.post("hello");

    }
}
