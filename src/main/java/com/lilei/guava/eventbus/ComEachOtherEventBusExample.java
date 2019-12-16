package com.lilei.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.lilei.guava.eventbus.service.QueryService;
import com.lilei.guava.eventbus.service.RequestQueryHandler;

/**
 * @description: 通过EventBus去解耦,解耦一些事情
 * @example: 比如有一个eventBus还有一个QueryService【用来查询的Service】,当查询信息的时候不会自己去查,会发送一个请求【request】给eventBus,
 *           有一个专门处理eventBus的一个线程RequestQueryHandler,收到了request【监听到了request】,当处理完之后,会给eventBus发送一个response,
 *           然后QueryService接收到了一个response,通过这个例子可以实现解耦
 * @author: Mr.Li
 * @date: Created in 2019/12/14 18:31
 * @version: 1.0
 * @modified By:
 */
public class ComEachOtherEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        // QueryService的构造方法中,QueryService把自己注册到了EventBus中
        QueryService queryService = new QueryService(eventBus);
        // EventBus对RequestQueryHandler进行了注册,并且把eventBus传到了RequestQueryHandler对象中
        eventBus.register(new RequestQueryHandler(eventBus));
        // 模拟查询功能
        queryService.query("orderNo1");
    }
}
