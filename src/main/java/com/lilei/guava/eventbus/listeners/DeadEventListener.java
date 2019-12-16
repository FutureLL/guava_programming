package com.lilei.guava.eventbus.listeners;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/14 14:44
 * @version: 1.0
 * @modified By:
 */
public class DeadEventListener {

    /**
     * DeadEvent introduce: Wraps an event that was posted, but which had no subscribers and thus could not be delivered.
     * Registering a DeadEvent subscriber is useful for debugging or logging, as it can detect misconfigurations in a system's event distribution.
     */
    @Subscribe
    public void handle(DeadEvent event) {
        System.out.println(event.getSource());  // DEAD-EVENT-BUS
        System.out.println(event.getEvent());   // Hello
    }
}
