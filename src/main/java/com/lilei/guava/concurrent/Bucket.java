package com.lilei.guava.concurrent;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

/**
 * @description: 模拟漏桶算法
 * @author: Mr.Li
 * @date: Created in 2019/12/17 10:50
 * @version: 1.0
 * @modified By:
 */
public class Bucket {

    // 定义一个"桶"
    private final ConcurrentLinkedQueue<Integer> container = new ConcurrentLinkedQueue<>();

    // 定义上沿
    private final static int BUCKET_LIMIT = 1000;

    // 下沿,也可以说是每秒桶处理的最大值
    private final RateLimiter RATE_LIMITER = RateLimiter.create(10);

    // 判断是否达到上沿,放数据
    private final Monitor OFFER_Monitor = new Monitor();
    // 消费元素
    private final Monitor POLL_Monitor = new Monitor();

    public void submit(Integer data) {
        // enterIf(Monitor.Guard guard): Enters this monitor if the guard is satisfied.
        if (OFFER_Monitor.enterIf(OFFER_Monitor.newGuard(() -> container.size() < BUCKET_LIMIT))) {
            try {
                container.offer(data);
                System.out.println(currentThread() + " submit data " + data + ", current size" + container.size());
            } finally {
                OFFER_Monitor.leave();
            }
        } else {
            throw new IllegalStateException("The bucket is full.");
        }
    }

    public void takeThenConsume(Consumer<Integer> consumer) {
        if (POLL_Monitor.enterIf(POLL_Monitor.newGuard(() -> !container.isEmpty()))) {
            try {
                // acquire(): Acquires a single permit from this RateLimiter, blocking until the request can be granted.
                System.out.println(currentThread() + " waiting " + RATE_LIMITER.acquire());
                consumer.accept(container.poll());
            } finally {
                POLL_Monitor.leave();
            }
        }
    }
}
