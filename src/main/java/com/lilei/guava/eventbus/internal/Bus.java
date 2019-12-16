package com.lilei.guava.eventbus.internal;

/**
 * @description: 定义一些方法的接口类
 * @author: Alex Wang
 * @date: Created in 2019/12/14 12:22
 * @version: 1.0
 * @modified By: Mr.Li
 */
public interface Bus {

    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);

    void post(Object event, String topic);

    void close();

    String getBusName();
}
