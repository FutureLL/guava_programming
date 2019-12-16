package com.lilei.guava.eventbus.internal;

/**
 * @description: 定义一个错误接口
 * @author: Alex Wang
 * @date: Created in 2019/12/14 12:22
 * @version: 1.0
 * @modified By: Mr.Li
 */
public interface MyEventExceptionHandler {
    void handle(Throwable cause, MyEventContext context);
}
