package com.lilei.guava.concurrent;

import com.google.common.util.concurrent.Monitor;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.currentThread;

/**
 * @description: 三种方式实现 --> 生产者消费者模型
 * @author: Mr.Li
 * @date: Created in 2019/12/16 15:21
 * @version: 1.0
 * @modified By:
 */
public class MonitorExample {

    /**
     * this -> monitor
     * synchronized说成加锁不严谨,应该是this关键字的monitor的lock,也就是this对象关联的monitor的lock
     * monitor回有一个计数器counter,只要有一个线程拿了这个monitor的lock,这个计数器会+1
     * synchronized(this){
     *     counter ++;
     *     synchronized(this){
     *         counter ++;
     *     }
     *     counter --;
     * }
     * counter --;
     */

    public static void main(String[] args) {

        // final LockCondition lc = new LockCondition();
        // final Synchronized sync = new Synchronized();
        final MonitorGuard monitorGuard = new MonitorGuard();

        final AtomicInteger COUNTER = new AtomicInteger(0);

        for (int i = 0; i < 3; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (; ; ) {
                        int data = COUNTER.getAndIncrement();
                        System.out.println(currentThread() + " offer " + data);
                        monitorGuard.offer(data);
                        try {
                            TimeUnit.MILLISECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        for (int i = 0; i < 3; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (; ; ) {
                        int data = monitorGuard.take();
                        System.out.println(currentThread() + " take " + data);
                        try {
                            TimeUnit.MILLISECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    static class MonitorGuard {
        private final LinkedList<Integer> queue = new LinkedList<>();

        private final int MAX = 10;

        private final Monitor monitor = new Monitor();

        // Monitor.Guard: A boolean condition for which a thread may wait.
        private final Monitor.Guard CAN_OFFER = new Monitor.Guard(monitor) {
            @Override
            public boolean isSatisfied() {
                return queue.size() <= MAX;
            }
        };

        // newGuard(BooleanSupplier isSatisfied): Creates a new guard for this monitor.
        private final Monitor.Guard CAN_TAKE = monitor.newGuard(() -> !queue.isEmpty());

        public void offer(int value) {
            try {
                // enterWhen(Monitor.Guard guard): Enters this monitor when the guard is satisfied[true].
                monitor.enterWhen(CAN_OFFER);
                queue.addLast(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // leave(): Leaves this monitor.
                monitor.leave();
            }
        }

        public int take() {
            try {
                monitor.enterWhen(CAN_TAKE);
                return queue.removeFirst();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                monitor.leave();
            }
        }
    }

    static class LockCondition {

        private final ReentrantLock lock = new ReentrantLock();

        private final Condition FULL_CONDITION = lock.newCondition();

        private final Condition EMPTY_CONDITION = lock.newCondition();

        private final LinkedList<Integer> queue = new LinkedList<>();

        private final int MAX = 10;

        public void offer(int value) {
            try {
                lock.lock();
                while (queue.size() >= MAX) {
                    FULL_CONDITION.await();
                }
                queue.addLast(value);
                EMPTY_CONDITION.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public int take() {
            Integer value = null;
            try {
                lock.lock();
                while (queue.isEmpty()) {
                    EMPTY_CONDITION.await();
                }
                value = queue.removeFirst();
                FULL_CONDITION.signalAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return value;
        }
    }

    static class Synchronized {
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int MAX = 10;

        public void offer(int value) {
            synchronized (queue) {
                while (queue.size() >= MAX) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                queue.add(value);
                queue.notifyAll();
            }
        }

        public int take() {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Integer value = queue.removeFirst();
                queue.notifyAll();
                return value;
            }
        }
    }
}
