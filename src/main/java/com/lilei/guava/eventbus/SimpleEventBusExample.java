package com.lilei.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.lilei.guava.eventbus.listeners.SimpleListener;

/**
 * @description: 注册的类中只有一个方法
 * @author: Mr.Li
 * @date: Created in 2019/12/14 10:27
 * @version: 1.0
 * @modified By:
 */
public class SimpleEventBusExample {

    public static void main(String[] args) {
        /**
         * 定义一个EventBus对象
         * EventBus introduction:
         *      Dispatches events to listeners, and provides ways for listeners to register themselves.
         *      The EventBus allows publish-subscribe-style communication between components
         *   without requiring the components to explicitly register with one another (and thus be aware of each other).
         *   It is designed exclusively to replace traditional Java in-process event distribution using explicit registration.
         *   It is not a general-purpose publish-subscribe system, nor is it intended for interprocess communication.
         */
        final EventBus eventBus = new EventBus();
        // register(Object object): Registers all subscriber methods on object to receive events.
        eventBus.register(new SimpleListener());
        System.out.println("post the simple event.");
        // post(Object event): Posts an event to all registered subscribers.
        eventBus.post("Simple Event");
    }
}
