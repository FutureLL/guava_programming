package com.lilei.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.lilei.guava.eventbus.listeners.ConcreteListener;

/**
 * @description: 注册的类中拥有父类,父类对这个注册的类的影响
 * @author: Mr.Li
 * @date: Created in 2019/12/14 12:09
 * @version: 1.0
 * @modified By:
 */
public class InheritListenerEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new ConcreteListener());
        System.out.println("post the String event");
        eventBus.post("I am string event");

        System.out.println("post the int event");
        // 没有对应类型的方法那么不处理
        eventBus.post(1000);
    }
}
