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
 * @date: Created in 2019/12/14 18:21
 * @version: 1.0
 * @modified By:
 */
public class QueryService {

    private final static Logger LOGGER = LoggerFactory.getLogger(QueryService.class);

    private final EventBus eventBus;

    public QueryService(EventBus eventBus) {
        this.eventBus = eventBus;
        // 把自己注册到EventBus中
        this.eventBus.register(this);
    }

    public void query(String orderNo){
        LOGGER.info("Received the orderNo [{}]",orderNo);
        this.eventBus.post(new Request(orderNo));
    }

    @Subscribe
    public void handleResponse(Response response){
        LOGGER.info("{}",response);
    }
}
