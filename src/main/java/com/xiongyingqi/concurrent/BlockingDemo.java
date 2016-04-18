package com.xiongyingqi.concurrent;

import java.util.*;
import java.util.concurrent.locks.*;

/**
 * @author <a href="http://xiongyingqi.com">qi</a>
 * @version 2015-09-28 11:11
 */
public class BlockingDemo {
    private final Condition     empty;
    private final Lock          lock;

    private LinkedList list = new LinkedList<>();

    public BlockingDemo() {
        lock = new ReentrantLock();
        empty = lock.newCondition();
    }

    public void add(String s) {
        try {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("put...");
            list.add(s);
            System.out.println("signal...");
            empty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object get() {
        try {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (list.isEmpty()) {
                try {
                    System.out.println("await...");
                    empty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Object o = list.getFirst();
            list.removeFirst();
            return o;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BlockingDemo blockingDemo = new BlockingDemo();
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Object a = blockingDemo.get();
                System.out.println(a);
            }
        });
        thread.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            blockingDemo.add(scanner.nextLine());
        }

    }

}
