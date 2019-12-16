package com.lilei.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.lilei.guava.eventbus.listeners.MultipleEventListeners;

/**
 * @description: 注册的类中多个方法,并且参数类型不同
 * @author: Mr.Li
 * @date: Created in 2019/12/14 12:09
 * @version: 1.0
 * @modified By:
 */
public class MultipleEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new MultipleEventListeners());
        System.out.println("post the String event");
        eventBus.post("I am string event");
        System.out.println("post the int event");
        eventBus.post(1000);
    }
}
