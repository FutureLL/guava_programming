package com.lilei.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.lilei.guava.eventbus.listeners.DeadEventListener;

/**
 * @description: DeadEvent
 * @author: Mr.Li
 * @date: Created in 2019/12/14 16:57
 * @version: 1.0
 * @modified By:
 */
public class DeadEventBusExample {

    public static void main(String[] args) {
        DeadEventListener deadEventListener = new DeadEventListener();
        final EventBus eventBus = new EventBus("DeadEventBus"){
            // 重写toString()
            @Override
            public String toString() {
                return "DEAD-EVENT-BUS";
            }
        };
        eventBus.register(deadEventListener);
        eventBus.post("Hello");

        // unregister(Object object): Unregisters all subscriber methods on a registered object.
        eventBus.unregister(deadEventListener);
        eventBus.post("Hello");
    }
}
