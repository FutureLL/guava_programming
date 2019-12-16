package com.lilei.guava.eventbus.monitor;

import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @description: 模拟监听某个目录,当目录发生变化的时候会被监听到
 * @author: Mr.Li
 * @date: Created in 2019/12/14 16:57
 * @version: 1.0
 * @modified By:
 */
public class MonitorClient {

    public static void main(String[] args) throws Exception {
        final EventBus eventBus = new EventBus();
        eventBus.register(new FileChangeListener());

        TargetMonitor monitor = new DirectoryTargetMonitor(eventBus, "E:\\monitor");
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        // 设置关闭监听的开启的时间【2分钟】
        ScheduledFuture<?> schedule = executorService.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    // 关闭监听
                    monitor.stopMonitor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2, TimeUnit.MINUTES);
        // 关闭线程
        executorService.shutdown();
        // 开启监听
        monitor.startMonitor();
    }
}

