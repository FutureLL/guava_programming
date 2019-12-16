package com.lilei.guava.eventbus.service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.lilei.guava.eventbus.events.Request;
import com.lilei.guava.eventbus.events.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/14 18:26
 * @version: 1.0
 * @modified By:
 */
public class RequestQueryHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestQueryHandler.class);

    private final EventBus eventBus;

    public RequestQueryHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    public void doQuery(Request request){
        LOGGER.info("start query the orderNo [{}]",request.toString());
        Response response = new Response();
        this.eventBus.post(response);
    }
}
