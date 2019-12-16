package com.lilei.guava.eventbus.internal;

import java.lang.reflect.Method;

/**
 * @description: 定义上下文接口
 * @author: Alex Wang
 * @date: Created in 2019/12/14 12:22
 * @version: 1.0
 * @modified By: Mr.Li
 */
public interface MyEventContext {

    String getSource();

    Object getSubscriber();

    Method getSubscribe();

    Object getEvent();
}
