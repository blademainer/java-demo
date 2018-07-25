package com.xiongyingqi.concurrent;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiongyingqi
 * @version 2016-05-17 20:14
 */
public class WaitAndNotifyDemo {
    public              AtomicInteger i     = new AtomicInteger(0);
    public static final int           SIZE  = 10;
    private final       Object        empty = new Object();
    private final       Object        full  = new Object();
    private volatile boolean isEmpty = false;
    private volatile boolean isFull = false;

    public void write() {
        while (i.get() >= SIZE) {
            synchronized (full) {
                isFull = true;
                try {
                    System.out.println("queue fulled...so waiting to read...");
                    full.wait();
                    System.out.println("Queue not full!! Continue push.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        isFull = false;
        this.i.incrementAndGet();

        if(isEmpty){
            synchronized (empty) {
                System.out.println("notify empty...");

                try {
                    empty.notifyAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void read() {
        while (i.get() <= 0) {
            synchronized (empty) {
                isEmpty = true;
                System.out.println("empty...");
                try {
                    empty.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Queue not empty!! Continue pull.");
            }
        }

        isEmpty = false;
        i.decrementAndGet();
        if(isFull){
            synchronized (full) {
                System.out.println("notify full...");
                try {
                    full.notifyAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Random random = new Random();

        WaitAndNotifyDemo demo = new WaitAndNotifyDemo();
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(random.nextInt(50) + 50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    demo.read();
                }
            }).start();
        }

        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(random.nextInt(50) + 50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    demo.write();
                }
            }).start();
        }

    }

}
