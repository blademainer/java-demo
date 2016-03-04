package com.xiongyingqi.cocurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiongyingqi
 * @version 2016-01-20 15:17
 */
public class AtomicDemo {
    private int max;
    private AtomicInteger atomicMax = new AtomicInteger();

    private int unsafeSetMax(int value) {
        if (value > max) {
            max = value;
        }
        return max;
    }

    private int safeSetMax(int value) {
        while (true) {
            int max = atomicMax.get();
            if (max > value) {
                return max;
            }
            boolean b = atomicMax.compareAndSet(max, value);
            if (b) {
                return value;
            }
        }
    }

    public static void testUnsafe() {
        for (int i = 0; i < 10000; i++) {

            Thread threadTest = new Thread(() -> {
                AtomicDemo atomicDemo = new AtomicDemo();
                Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    atomicDemo.unsafeSetMax(2);
                });
                Thread thread2 = new Thread(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    atomicDemo.unsafeSetMax(3);
                });
                thread.start();
                thread2.start();

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (atomicDemo.max == 2) {
                    System.out.println(atomicDemo.max);
                    System.exit(1);
                }
            });

            threadTest.start();
        }

    }

    public static void testSafe() {
        for (int i = 0; i < 10000; i++) {

            Thread threadTest = new Thread(() -> {
                AtomicDemo atomicDemo = new AtomicDemo();
                Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    atomicDemo.safeSetMax(2);
                });
                Thread thread2 = new Thread(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    atomicDemo.safeSetMax(3);
                });
                thread.start();
                thread2.start();

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (atomicDemo.atomicMax.get() == 2) {
                    System.out.println("return... " + atomicDemo.atomicMax.get());
                    System.exit(1);
                }
            });

            threadTest.start();
        }

    }

    public static void main(String[] args) {
        //        testUnsafe();
        testSafe();
    }
}
