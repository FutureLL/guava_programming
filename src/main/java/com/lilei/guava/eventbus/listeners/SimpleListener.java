package com.lilei.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/14 9:20
 * @version: 1.0
 * @modified By:
 */
public class SimpleListener {

    // getLogger(): Return a logger named corresponding to the class passed as parameter, using the statically bound ILoggerFactory instance.
    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleListener.class);

    @Subscribe
    public void doAction(final String event) {
        // isInfoEnabled(): Is the logger instance enabled for the INFO level?
        if (LOGGER.isInfoEnabled()) {
            // info​(String msg, Object arg): Log a message at the INFO level according to the specified format and argument.
            LOGGER.info("Received event [{}] and will take a action", event);
        }
    }
}
