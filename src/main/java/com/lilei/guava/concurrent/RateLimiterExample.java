package com.lilei.guava.concurrent;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.*;

import static java.lang.Thread.currentThread;

/**
 * @description: 令牌桶算法实现流量限制 RateLimiter
 * @author: Mr.Li
 * @date: Created in 2019/12/16 21:20
 * @version: 1.0
 * @modified By:
 *
 * 实例代码: RateLimiter 和 Semaphore 的实例
 *
 * 常用的限流算法
 *      漏桶算法
 *          漏桶算法思路很简单,水（请求）先进入到漏桶里,漏桶以一定的速度出水,当水流入速度过大会直接溢出,
 *          可以看出漏桶算法能强行限制数据的传输速率。
 *
 *      令牌桶算法
 *          对于很多应用场景来说,除了要求能够限制数据的平均传输速率外,还要求允许某种程度的突发传输。
 *          这时候漏桶算法可能就不合适了,令牌桶算法更为适合。如图所示,令牌桶算法的原理是系统会以一
 *          个恒定的速度往桶里放入令牌,而如果请求需要被处理,则需要先从桶里获取一个令牌,当桶里没有令牌可取时,
 *          则拒绝服务。
 *
 */
public class RateLimiterExample {

    // create(double permitsPerSecond): Creates a RateLimiter with the specified stable throughput, given as "permits per second" (commonly referred to as QPS, queries per second).
    // 换句话说就是一秒钟只能有0.5次的操作
    private final static RateLimiter RATE_LIMITER = RateLimiter.create(0.5d);

    // 同一时刻只有三个线程访问该方法
    private final static Semaphore SEMAPHORE = new Semaphore(3);

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        /**
        IntStream.range(0, 10).forEach(i ->
            service.submit(RateLimiterExample::testLimiter)
        );


        for (int i = 0; i < 10; i++) {
            service.submit(RateLimiterExample::testLimiter);
        }
         */

        for (int i = 0; i < 10; i++) {
            service.submit(RateLimiterExample::testSemaphore);
        }
    }

    private static void testLimiter() {
        // acquire(): Acquires a single permit from this RateLimiter, blocking until the request can be granted.
        System.out.println(currentThread() + " waiting " + RATE_LIMITER.acquire());
    }

    private static void testSemaphore() {
        try {
            SEMAPHORE.acquire();
            System.out.println(currentThread() + " is coming and do work. ");
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            SEMAPHORE.release();
            System.out.println(currentThread() + " release the SEMAPHORE. ");
        }
    }
}
