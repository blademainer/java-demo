package com.xiongyingqi.concurrent;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xiongyingqi
 * @version 2016-04-15 15:05
 */
public class ReadWriteLockDemo {
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private ArrayList<String> collection = new ArrayList<String>();

    public static final int MAX_SIZE = 100;

    public void write(String s) {
        writeLock.lock();
        try {
            collection.add(s);
        } finally {
            writeLock.unlock();
        }
    }

    public String read() {
        readLock.lock();
        try {
            if (!collection.isEmpty()) {
                String s = collection.get(collection.size() - 1);
                collection.remove(collection.size() - 1);
                return s;
            }
        } finally {
            readLock.unlock();
        }
        return null;
    }

    public static void main(String[] args) {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();

        new Thread(() -> {
            int i = 1;
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                demo.write("sss" + i);
                i += 2;
            }
        }).start();

        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                demo.write("sss" + i);
                i += 2;
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "    " + demo.read());
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "    " + demo.read());
            }
        }).start();

    }

}
