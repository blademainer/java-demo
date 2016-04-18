package com.xiongyingqi.concurrent;

/**
 * @author xiongyingqi
 * @version 2016-04-18 17:35
 */
public class SynchronousDemo {
    public static synchronized void log1(String msg1, String msg2) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("log1...msg1: " + msg1);
        System.out.println("log1...msg2: " + msg2);
    }

    public static void log2(String msg1, String msg2) {
        synchronized (SynchronousDemo.class) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("log2...msg1: " + msg1);
            System.out.println("log2...msg2: " + msg2);
        }
    }

    public static void log3(String msg1, String msg2) {
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("log3...msg1: " + msg1);
        System.out.println("log3...msg2: " + msg2);
    }

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                log1(i + "", i + "");  // synchronous with log2
            }
        }).start();
        new Thread(() -> {
            for (int i = 100; i < 200; i++) {
                log2(i + "", i + ""); // synchronous with log1
            }
        }).start();
        new Thread(() -> {
            for (int i = 200; i < 300; i++) {
                log3(i + "", i + ""); // asynchronous with log1 and log2, must faster than them!
            }
        }).start();
    }
}
