package com.lilei.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/14 14:00
 * @version: 1.0
 * @modified By:
 */
public class ExceptionListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionListener.class);

    @Subscribe
    public void m1(String event) {
        // info​(String msg, Object arg): Log a message at the INFO level according to the specified format and argument.
        LOGGER.info("===== m1 ===== {}", event);
    }

    @Subscribe
    public void m2(String event) {
        LOGGER.info("===== m2 ===== {}", event);
    }

    @Subscribe
    public void m3(String event) {
        LOGGER.info("===== m3 ===== {}", event);
        // 抛出一个异常,模拟对event的影响
        throw new RuntimeException();
    }
}
