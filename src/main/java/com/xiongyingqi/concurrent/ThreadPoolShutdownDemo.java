package com.xiongyingqi.concurrent;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qi
 * @since 2018/5/30
 */
public class ThreadPoolShutdownDemo {

    static AtomicInteger count = new AtomicInteger();
    static AtomicInteger shutDownCount = new AtomicInteger();


    public static void shutdown() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1000, true));
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executor.submit(() -> {
                Thread.currentThread().setName("shutdown-" + finalI);
                System.out.println(Thread.currentThread() + " Job starting..." + finalI);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                    System.out.println(Thread.currentThread() + " Interrupted: " + e.getMessage());
                }
                System.out.println(Thread.currentThread() + " Job done..." + finalI);
                shutDownCount.incrementAndGet();
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void shutdownNow() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1000, true));
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executor.submit(() -> {
                Thread.currentThread().setName("shutdownNow-i=====" + finalI);
                System.out.println(Thread.currentThread() + " Job starting..." + finalI);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                    System.out.println(Thread.currentThread() + " Interrupted: " + e.getMessage() + " thread.interrupted? " + Thread.interrupted());
                }
                System.out.println(Thread.currentThread() + " Job done..." + finalI + " thread.interrupted? " + Thread.interrupted());
                count.incrementAndGet();
            });
        }

        List<Runnable> runnables = executor.shutdownNow();
        runnables.forEach((r) -> {
            new Thread(r).start();
        });
//        try {
//            executor.awaitTermination(30, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            shutdown();
        });
        thread.start();
        Thread thread2 = new Thread(() -> {
            shutdownNow();
        });
        thread2.start();
        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("shutdownCount: " + shutDownCount.get());
        System.out.println("shutdownNowCount: " + count.get());
    }
}
