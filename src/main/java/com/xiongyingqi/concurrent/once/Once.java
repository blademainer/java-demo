package com.xiongyingqi.concurrent.once;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiongyingqi
 * @since 2018/7/23
 */
public class Once {
    private AtomicBoolean done = new AtomicBoolean(false);
    private Lock lock = new ReentrantLock();

    public void action(OnceFunc onceFunc) {
        if (done.get()) {
            System.out.println(Thread.currentThread() + "Already done!!!");
            return;
        }
        lock.lock();
        try {
            if (!done.get()) {
                try {
                    onceFunc.action();
                } finally {
                    done.compareAndSet(false, true);
                }
            } else {
                System.out.println(Thread.currentThread() + "Another thread already done!!!");
            }
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Once once = new Once();
        List<Thread> list = new ArrayList<Thread>();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                once.action(() -> {
                    System.out.println("doing... once!!");
                });
            });
            list.add(thread);
            thread.start();
        }

        for (Thread thread : list) {
            thread.join();
        }


    }

}
