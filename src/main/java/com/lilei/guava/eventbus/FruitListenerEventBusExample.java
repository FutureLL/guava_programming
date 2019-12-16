package com.lilei.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.lilei.guava.eventbus.events.Apple;
import com.lilei.guava.eventbus.events.Fruit;
import com.lilei.guava.eventbus.listeners.FruitEaterListener;

/**
 * @description: 向注册类中注册的类中还有的方法需要的参数是另外一个类,不再是String,Integer
 * @author: Mr.Li
 * @date: Created in 2019/12/14 12:09
 * @version: 1.0
 * @modified By:
 */
public class FruitListenerEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new FruitEaterListener());
        eventBus.post(new Apple("apple"));
        System.out.println("==============================================");
        eventBus.post(new Fruit("apple"));
    }
}
