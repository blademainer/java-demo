package com.xiongyingqi.concurrent;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiongyingqi
 * @version 2016-04-15 15:05
 */
public class BlockingQueueDemo {
    private ReentrantLock lock = new ReentrantLock();

    private Condition empty = lock.newCondition();

    private Condition full = lock.newCondition();

    private ArrayList<String> collection = new ArrayList<String>();

    public static final int MAX_SIZE = 2;

    public void write(String s) {
        lock.lock();
        try {
            while (collection.size() > MAX_SIZE) {
                System.out.println("waiting.... full..");
                try {
                    full.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            collection.add(s);
            empty.signal();
        } finally {
            lock.unlock();
        }
    }

    public String read() {
        lock.lock();
        try {
            while (collection.isEmpty()) {
                System.out.println("waiting.... empty..");
                try {
                    empty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String s = collection.get(collection.size() - 1);
            collection.remove(collection.size() - 1);
            full.signal();
            return s;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BlockingQueueDemo demo = new BlockingQueueDemo();
        final int wait = 100;

        Random random = new Random();
        new Thread(() -> {
            int i = 1;
            while (true) {
                try {
                    Thread.sleep(random.nextInt(wait) + wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                demo.write("sss" + i);
                i += 1;
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(random.nextInt(wait) + wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(demo.read());
            }
        }).start();

    }

}
