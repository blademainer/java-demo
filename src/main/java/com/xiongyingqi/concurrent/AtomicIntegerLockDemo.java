package com.xiongyingqi.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiongyingqi
 * @version 2016-01-20 15:17
 */
public class AtomicIntegerLockDemo {
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private int           i             = 0;

    private void increase() {
        while (!atomicInteger.compareAndSet(0, 1)) {
        }
        try {
            i = i + 1;
        } finally {
            atomicInteger.compareAndSet(1, 0);
        }
    }

    public static void testSafe() throws InterruptedException {
        AtomicIntegerLockDemo atomicDemo = new AtomicIntegerLockDemo();
        int size = 100;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atomicDemo.increase();
            });
            thread.start();
            threads[i] = thread;
        }

        for (Thread thread : threads) {
            thread.join();
        }
        if (atomicDemo.i != 100) {
            System.err.println("return... " + atomicDemo.i);
        } else {
            System.out.println("return... " + atomicDemo.i);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        //        testUnsafe();
        for (int i = 0; i < 20; i++) {
            testSafe();
        }
    }
}
