package com.lilei.guava.concurrent;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.*;

/**
 * @description: ListenableFuture、Futures and Java8
 * @author: Mr.Li
 * @date: Created in 2019/12/17 17:16
 * @version: 1.0
 * @modified By:
 */
public class ListenableFutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);

        // 想要得到结果,就必须要等到线程执行完之后才可以
        Future<Integer> future = service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return 10;
        });

        Integer result = future.get();
        System.out.println(result);

        /** ============================================================================================== */

        // 不在需要进行get()方法调用
        /**
         * ListeningExecutorService: An ExecutorService that returns ListenableFuture instances.
         * MoreExecutors: Factory and utility methods for Executor, ExecutorService, and ThreadFactory.
         * listeningDecorator(): Creates an ExecutorService whose submit and invokeAll methods submit ListenableFutureTask instances to the given delegate executor.
         */
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(service);
        ListenableFuture<Integer> listFuture = listeningExecutorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return 100;
        });

        // addListener(Runnable listener, Executor executor): Registers a listener to be run on the given executor.
        // 但是这种方式没办法接受返回值
        listFuture.addListener(() -> {
            System.out.println("I am finished");
        }, service);

        /** ============================================================================================== */

        // 这种方式可以接受返回值,返回值通过回调的方式
        Futures.addCallback(listFuture, new MyCallBack(), service);

        /** ============================================================================================== */

        // 使用Java8的方式
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return 100;
        }, service).whenComplete((v, t) -> {
            System.out.println("I am finished and the result is: " + v);
        });
    }

    static class MyCallBack implements FutureCallback<Integer> {

        @Override
        public void onSuccess(@Nullable Integer result) {
            System.out.println("I am finished and the result is: " + result);
        }

        @Override
        public void onFailure(Throwable t) {
            t.printStackTrace();
        }
    }
}
