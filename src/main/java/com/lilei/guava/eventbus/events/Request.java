package com.lilei.guava.eventbus.events;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/14 18:20
 * @version: 1.0
 * @modified By:
 */
public class Request {

    private final String orderNo;

    public Request(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "Request{" +
                "orderNo='" + orderNo + '\'' +
                '}';
    }
}
