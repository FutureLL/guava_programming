package com.lilei.guava.eventbus.monitor;

/**
 * @description: 定义两个接口方法, 分别表示开始监听以及停止监听
 * @author: Mr.Li
 * @date: Created in 2019/12/14 12:09
 * @version: 1.0
 * @modified By:
 */
public interface TargetMonitor {

    /**
     * 定义两个接口方法,分别表示开始监听以及停止监听
     */
    void startMonitor() throws Exception;

    void stopMonitor() throws Exception;
}