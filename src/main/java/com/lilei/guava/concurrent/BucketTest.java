package com.lilei.guava.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

/**
 * @description: Bucket的测试类
 * @author: Mr.Li
 * @date: Created in 2019/12/17 11:35
 * @version: 1.0
 * @modified By:
 */
public class BucketTest {

    public static void main(String[] args) {

        final Bucket bucket = new Bucket();
        // An int value that may be updated atomically.
        final AtomicInteger DATA_CREATOR = new AtomicInteger(0);

        // 一秒提交五个,五个线程就是25个
        IntStream.range(0, 5).forEach(i -> {
            new Thread(() -> {
                for (; ; ) {
                    int data = DATA_CREATOR.getAndIncrement();
                    bucket.submit(data);
                    try {
                        TimeUnit.MILLISECONDS.sleep(200L);
                    } catch (Exception e) {
                        if (e instanceof IllegalStateException) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }).start();
        });

        // Bucket每秒可以处理10个线程
        // 25:10 ==> 5:2

        // 工作线程
        IntStream.range(0, 5).forEach(i -> {
            new Thread(() -> {
                for (; ; ) {
                    bucket.takeThenConsume(x -> {
                        System.out.println(currentThread() + " W " + x);
                    });
                }
            }).start();
        });
    }
}
