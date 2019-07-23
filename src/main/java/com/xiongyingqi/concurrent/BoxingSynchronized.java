package com.xiongyingqi.concurrent;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qi
 * @since 2018/7/11
 */
public class BoxingSynchronized {
    private Integer i = new Integer(0);

    /**
     * bad lock
     */
    public int increase() {
        synchronized (i) {
            System.out.println(Thread.currentThread() + " locked: " + i.hashCode());
            i++;
        }
        return i;
    }

    public static void main(String[] args) {
        BoxingSynchronized boxingSynchronized = new BoxingSynchronized();
        List<Thread> threadList = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i1 = 0; i1 < 1000; i1++) {
                        int increase = boxingSynchronized.increase();
                        System.out.println(Thread.currentThread() + ": current i = " + increase);
                    }

                }
            };
            thread.start();
            threadList.add(thread);
        }

        threadList.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("final i: " + boxingSynchronized.increase());
    }
}
