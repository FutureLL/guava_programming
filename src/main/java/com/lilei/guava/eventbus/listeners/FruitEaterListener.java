package com.lilei.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import com.lilei.guava.eventbus.events.Apple;
import com.lilei.guava.eventbus.events.Fruit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/14 12:48
 * @version: 1.0
 * @modified By:
 */
public class FruitEaterListener {

    // getLogger(): Return a logger named corresponding to the class passed as parameter, using the statically bound ILoggerFactory instance.
    private final static Logger LOGGER = LoggerFactory.getLogger(FruitEaterListener.class);

    @Subscribe
    public void eat(Fruit event) {
        // isInfoEnabled(): Is the logger instance enabled for the INFO level?
        if (LOGGER.isInfoEnabled()) {
            // info​(String msg, Object arg): Log a message at the INFO level according to the specified format and argument.
            LOGGER.info("Fruit.eat [{}]", event);
        }
    }

    @Subscribe
    public void eat(Apple event) {
        // isInfoEnabled(): Is the logger instance enabled for the INFO level?
        if (LOGGER.isInfoEnabled()) {
            // info​(String msg, Object arg): Log a message at the INFO level according to the specified format and argument.
            LOGGER.info("Apple.eat [{}]", event);
        }
    }
}
