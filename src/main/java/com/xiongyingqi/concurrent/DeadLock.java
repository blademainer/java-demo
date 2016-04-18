package com.xiongyingqi.concurrent;

/**
 * @author xiongyingqi
 * @version 2016-04-18 18:49
 */
public class DeadLock {
    public static void main(String[] args) {
        //        LockOne o = new LockOne();
        //        LockOne t = new LockOne();
        //        new Thread(() -> {
        //            o.tryLock(t);
        //        }).start();
        //        new Thread(() -> {
        //            t.tryLock(o);
        //        }).start();

        LockTwo a = new LockTwo();
        LockTwo b = new LockTwo();
        new Thread(() -> {
            a.tryLock(b);
        }).start();
        new Thread(() -> {
            b.tryLock(a);
        }).start();

    }
}

class LockOne {

    public LockOne() {
    }

    public synchronized void tryLock(LockOne o) {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("try lock " + o + " thread: " + Thread.currentThread());
        o.action();
    }

    public synchronized void action() {
        System.out.println("action...  thread: " + Thread.currentThread());
    }

}

class LockTwo {

    public LockTwo() {
    }

    public synchronized void tryLock(LockTwo o) {
        synchronized (this) {
            System.out.println("try lock " + o + " thread: " + Thread.currentThread());
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            o.action();
        }

        //        o.action();
    }

    public synchronized void action() {
        synchronized (this) {
            System.out.println("action...  thread: " + Thread.currentThread());
        }
    }

}
