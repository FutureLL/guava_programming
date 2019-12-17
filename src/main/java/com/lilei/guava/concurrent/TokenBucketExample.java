package com.lilei.guava.concurrent;

/**
 * @description: 模拟令牌桶算法测试类
 * @author: Mr.Li
 * @date: Created in 2019/12/17 15:59
 * @version: 1.0
 * @modified By:
 */
public class TokenBucketExample {

    public static void main(String[] args) {
        final TokenBucket tokenBucket = new TokenBucket();

        // 110个人抢
        for (int i = 0; i < 110; i++) {
            new Thread(tokenBucket :: buy).start();
        }
    }
}
