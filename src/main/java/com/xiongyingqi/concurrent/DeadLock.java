package com.xiongyingqi.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiongyingqi
 * @version 2016-04-18 18:49
 */
public class DeadLock {

    public static void lockByThree() {
        LockThree a = new LockThree();
        LockThree b = new LockThree();
        new Thread(() -> {
            a.tryLock(b);
        }).start();
        new Thread(() -> {
            b.tryLock(a);
        }).start();
    }

    public static void lockByTwo() {
        LockTwo a = new LockTwo();
        LockTwo b = new LockTwo();
        new Thread(() -> {
            a.tryLock(b);
        }).start();
        new Thread(() -> {
            b.tryLock(a);
        }).start();
    }
    public static void lockByFour() {
        LockFour a = new LockFour();
        new Thread(() -> {
            a.lockOne();
        }).start();
        new Thread(() -> {
            a.lockTwo();
        }).start();
    }

    public static void main(String[] args) {
        //        LockOne o = new LockOne();
        //        LockOne t = new LockOne();
        //        new Thread(() -> {
        //            o.tryLock(t);
        //        }).start();
        //        new Thread(() -> {
        //            t.tryLock(o);
        //        }).start();

//        lockByTwo();
        lockByFour();

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
        System.out.println("try lock... " + this);
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
        System.out.println("try lock... " + this);
        synchronized (this) {
            System.out.println("action...  thread: " + Thread.currentThread());
        }
    }

}

class LockThree {

    public LockThree() {
    }

    public synchronized void tryLock(Object o) {
        System.out.println("try lock... " + getClass());
        synchronized (toString().intern()) {
            System.out.println("try lock " + o + " thread: " + Thread.currentThread());
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("try lock... " + o.getClass());
            synchronized (o.toString().intern()) {
                System.out.println("action...  thread: " + Thread.currentThread());
            }
        }

        //        o.action();
    }

}

class LockFour {
    static ReentrantLock lockOne = new ReentrantLock();
    static ReentrantLock lockTwo = new ReentrantLock();
    public void lockOne() {
        lockOne.lock();
        try {
            Thread.sleep(100L);
            lockTwo.lock();
            try {
                System.out.println("not a dead lock!");
            }finally {
                lockTwo.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lockOne.unlock();
        }
    }

    public void lockTwo() {
        lockTwo.lock();
        try {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lockOne.lock();
            try {
                System.out.println("not a dead lock!");
            }finally {
                lockOne.unlock();
            }
        } finally {
            lockTwo.unlock();
        }
    }
}
