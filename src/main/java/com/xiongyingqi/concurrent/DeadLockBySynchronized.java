package com.xiongyingqi.concurrent;

/**
 * @author xiongyingqi
 * @since 17-2-27 下午2:10
 */
public class DeadLockBySynchronized {
    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public void runA() throws InterruptedException {
        synchronized (lockA) {
            Thread.sleep(100);
            System.out.println("Thread: " + Thread.currentThread().getId() + " in lock A");
            synchronized (lockB) {
                System.out.println("Thread: " + Thread.currentThread().getId() + " in lock B");
            }
        }
    }

    public void runB() throws InterruptedException {
        synchronized (lockB) {
            Thread.sleep(100);
            System.out.println("Thread: " + Thread.currentThread().getId() + " in lock B");
            synchronized (lockA) {
                System.out.println("Thread: " + Thread.currentThread().getId() + " in lock A");
            }
        }
    }

    public static void main(String[] args) {
        DeadLockBySynchronized deadLockBySynchronized = new DeadLockBySynchronized();
        new Thread(() -> {
            try {
                deadLockBySynchronized.runA();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                deadLockBySynchronized.runB();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
