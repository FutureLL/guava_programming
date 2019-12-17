package com.lilei.guava.concurrent;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

/**
 * @description: 模拟令牌桶算法
 * @author: Mr.Li
 * @date: Created in 2019/12/17 14:49
 * @version: 1.0
 * @modified By:
 */
public class TokenBucket {

    // 定义规定时间内卖了商品的个数
    private AtomicInteger phoneNumbers = new AtomicInteger(0);

    // 定义最多运行的线程数量,出售100台
    private final static int LIMIT = 100;

    // 也可以说是每秒桶处理的最大值,最大抢购人数
    private RateLimiter RATE_LIMITER = RateLimiter.create(10);

    private final int saleLimit;

    public TokenBucket() {
        this(LIMIT);
    }

    public TokenBucket(int limit) {
        this.saleLimit = limit;
    }

    public int buy() {
        Stopwatch started = Stopwatch.createStarted();

        /**
         * tryAcquire(long timeout, TimeUnit unit):
         *   Acquires a permit from this RateLimiter if it can be obtained without exceeding the specified timeout, or
         *   returns false immediately (without waiting) if the permit would not have been granted before the timeout expired.
         */
        boolean success = RATE_LIMITER.tryAcquire(10, TimeUnit.SECONDS);
        if (success) {
            if (phoneNumbers.get() >= saleLimit) {
                throw new IllegalStateException("Not any phone can be sale, please wait to next time.");
            }
            int phoneNo = phoneNumbers.getAndIncrement();
            handleOrder();
            System.out.println(currentThread() + " user get the Mi phone: " + phoneNo + ",ELT:" + started.stop());
            return phoneNo;
        } else {
            started.stop();
            throw new RuntimeException("Sorry, occur exception when buy phone");
        }
    }

    private void handleOrder() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}