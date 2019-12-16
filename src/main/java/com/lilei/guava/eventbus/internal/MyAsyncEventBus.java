package com.lilei.guava.eventbus.internal;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: EventBus的子类
 * @author: Alex Wang
 * @date: Created in 2019/12/14 12:22
 * @version: 1.0
 * @modified By: Mr.Li
 */
public class MyAsyncEventBus extends MyEventBus {

    public MyAsyncEventBus(String busName, MyEventExceptionHandler exceptionHandler, ThreadPoolExecutor executor) {
        super(busName, exceptionHandler, executor);
    }


    public MyAsyncEventBus(String busName, ThreadPoolExecutor executor) {
        this(busName, null, executor);
    }

    public MyAsyncEventBus(ThreadPoolExecutor executor) {
        this("default-async", null, executor);
    }

    public MyAsyncEventBus(MyEventExceptionHandler exceptionHandler, ThreadPoolExecutor executor) {
        this("default-async", exceptionHandler, executor);
    }
}
