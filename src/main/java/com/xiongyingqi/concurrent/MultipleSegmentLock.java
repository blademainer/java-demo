package com.xiongyingqi.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiongyingqi
 * @version 2016-04-18 16:51
 */
public class MultipleSegmentLock {
    private Map<String, ReentrantLock> lockMap = new ConcurrentHashMap<String, ReentrantLock>();

    public void write(String key, String value) {
        Lock lock = checkLock(key);
        lock.lock();
        try {
            System.out.println("writing... " + key + "=" + value);
            try {
                //                Random random = new Random();
                //                long time = random.nextInt(10) + 10;
                //                Thread.sleep(time);
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("write complete... " + key + "=" + value);
        } finally {
            lock.unlock();
        }
    }

    public void read(String key) {
        Lock lock = checkLock(key);
        lock.lock();
        try {
            System.out.println("reading... " + key);
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("read complete... " + key);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Getting the lock of the key. Create a {@link ReentrantLock} when not exists.
     * <p>Implements with double check</p>
     *
     * @param key Segment by the key
     * @return {@link ReentrantLock}
     */
    private Lock checkLock(String key) {
        ReentrantLock reentrantLock = lockMap.get(key);
        if (reentrantLock == null) {
            synchronized (this) {
                reentrantLock = lockMap.get(key);
                if (reentrantLock == null) {
                    reentrantLock = new ReentrantLock();
                    System.out.println(
                            "lock for " + key + " not exists! so create a lock: " + reentrantLock);
                    lockMap.put(key, reentrantLock);
                    return reentrantLock;
                }
                return reentrantLock;
            }
        }
        return reentrantLock;
    }

    public static void main(String[] args) {
        MultipleSegmentLock multipleSegmentLock = new MultipleSegmentLock();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                multipleSegmentLock.write("key",
                        "" + i); // synchronous with 'key' and asynchronous with 'key2'
            }
        }).start();
        new Thread(() -> {
            for (int i = 100; i < 200; i++) {
                multipleSegmentLock.write("key",
                        "" + i); // synchronous with 'key' and asynchronous with 'key2'
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                multipleSegmentLock.write("key2",
                        "" + i); // synchronous with 'key2' and asynchronous with 'key'
            }
        }).start();
        new Thread(() -> {
            for (int i = 100; i < 200; i++) {
                multipleSegmentLock.write("key2",
                        "" + i); // synchronous with 'key2' and asynchronous with 'key'
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                multipleSegmentLock.read("key");
            }
        }).start();

        // Console out may be:
        // ----------------------------------------------------
        //lock for key not exists! so create a lock: null
        //lock for key2 not exists! so create a lock: null
        //writing... key=0
        //writing... key2=0
        //write complete... key=0
        //write complete... key2=0
        //writing... key=1
        //writing... key2=1
        //write complete... key=1
        //write complete... key2=1
        //writing... key2=2
        //writing... key=2
        //write complete... key2=2
        //write complete... key=2
        //writing... key2=3
        //writing... key=3
        //write complete... key2=3
        //write complete... key=3
        //... ...
        //reading... key
        //write complete... key2=49
        //read complete... key
        //reading... key
        //writing... key2=50
        //read complete... key
        //write complete... key2=50
        //reading... key
        //writing... key2=51
        //read complete... key
        //write complete... key2=51
        //reading... key
        //writing... key2=109
        //read complete... key
        //write complete... key2=109
        //reading... key
        //writing... key2=110
        //read complete... key
        //... ...
        //writing... key=194
        //write complete... key=194
        //writing... key=195
        //write complete... key=195
        //writing... key=196
        //write complete... key=196
        //writing... key=197
        //write complete... key=197
        //writing... key=198
        //write complete... key=198
        //writing... key=199
        //write complete... key=199
        // ----------------------------------------------------
    }

}
