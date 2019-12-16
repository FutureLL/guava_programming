package com.lilei.guava.eventbus.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: Alex Wang
 * @date: Created in 2019/12/14 12:22
 * @version: 1.0
 * @modified By: Mr.Li
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MySubscribe {
    String topic() default "default-topic";
}
