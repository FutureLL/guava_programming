package com.lilei.guava.eventbus.events;

import com.google.common.base.MoreObjects;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/14 12:44
 * @version: 1.0
 * @modified By:
 */
public class Fruit {

    private final String name;

    public Fruit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("Name", name).toString();
    }
}
