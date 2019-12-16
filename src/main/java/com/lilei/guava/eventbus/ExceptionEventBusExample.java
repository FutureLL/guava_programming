package com.lilei.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.lilei.guava.eventbus.listeners.ExceptionListener;

import javax.swing.text.AbstractDocument;
import java.util.logging.Handler;

/**
 * @description: 异常对event的影响, 互不干扰, 有异常抛出, 没异常输出
 * @author: Mr.Li
 * @date: Created in 2019/12/14 14:04
 * @version: 1.0
 * @modified By:
 */
public class ExceptionEventBusExample {

    public static void main(String[] args) {
        // final EventBus eventBus = new EventBus(new ExceptionHandler());
        final EventBus eventBus = new EventBus((exception,context) ->{
            System.out.println("getEvent(): " + context.getEvent());
            System.out.println("getEventBus(): " + context.getEventBus());
            System.out.println("getSubscriber(): " + context.getSubscriber());
            System.out.println("getSubscriberMethod(): " + context.getSubscriberMethod());
        });
        eventBus.register(new ExceptionListener());
        eventBus.post("exception post");
    }

    /** SubscriberExceptionHandler: Handler for exceptions thrown by event subscribers.*/
    static class ExceptionHandler implements SubscriberExceptionHandler {

        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context) {
            System.out.println("getEvent(): " + context.getEvent());
            System.out.println("getEventBus(): " + context.getEventBus());
            System.out.println("getSubscriber(): " + context.getSubscriber());
            System.out.println("getSubscriberMethod(): " + context.getSubscriberMethod());
        }
    }
}
