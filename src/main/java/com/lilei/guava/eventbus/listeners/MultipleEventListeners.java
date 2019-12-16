package com.lilei.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/14 11:43
 * @version: 1.0
 * @modified By:
 */
public class MultipleEventListeners {

    // getLogger(): Return a logger named corresponding to the class passed as parameter, using the statically bound ILoggerFactory instance.
    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleListener.class);

    @Subscribe
    public void tesk1(String event) {
        // isInfoEnabled(): Is the logger instance enabled for the INFO level?
        if (LOGGER.isInfoEnabled()) {
            // info​(String msg, Object arg): Log a message at the INFO level according to the specified format and argument.
            LOGGER.info("Received event [{}] received and will take a action by == tesk1 == ", event);
        }
    }

    @Subscribe
    public void tesk2(String event) {
        // isInfoEnabled(): Is the logger instance enabled for the INFO level?
        if (LOGGER.isInfoEnabled()) {
            // info​(String msg, Object arg): Log a message at the INFO level according to the specified format and argument.
            LOGGER.info("Received event [{}] received and will take a action by == tesk2 == ", event);
        }
    }

    @Subscribe
    public void intTask(Integer event) {
        // isInfoEnabled(): Is the logger instance enabled for the INFO level?
        if (LOGGER.isInfoEnabled()) {
            // info​(String msg, Object arg): Log a message at the INFO level according to the specified format and argument.
            LOGGER.info("Received event [{}] received and will take a action by == intTask == ", event);
        }
    }
}
