package com.xiongyingqi.concurrent;

import org.junit.Assert;

import java.util.Arrays;

/**
 * @author xiongyingqi
 * @since 16-9-26 上午11:29
 */
public class SynchronizedDemoTest {
    @org.junit.Test
    public void runByLock() throws Exception {
        int threadSize = 10;
        int loopSize = 1000;

        Object[] locks = new Object[threadSize];
        for (int i = 0; i < threadSize; i++) {
            Object lock = new Object();
            locks[i] = lock;
        }

        int[] sum = new int[threadSize];

        Thread[][] threads = new Thread[threadSize][];
        for (int i = 0; i < threadSize; i++) {
            threads[i] = new Thread[threadSize];
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int j = 0; j < threadSize; j++) {
                int finalI = i;
                Thread thread = new Thread(() -> {
                    for (int i1 = 0; i1 < loopSize; i1++) {

                        Object lock = locks[finalI];
                        SynchronizedDemo.runByLock(lock, integer -> {
                            sum[integer]++;
                            //                            System.out.println(integer + "==" + sum[integer]);
                            return integer;
                        }, finalI);

                    }
                });
                thread.start();
                threads[i][j] = thread;
            }
        }
        for (int i = 0; i < threads.length; i++) {
            for (int j = 0; j < threads[i].length; j++) {
                threads[i][j].join();
            }
        }
        System.out.println(Arrays.toString(sum));
        for (int i : sum) {
            System.out.println(i);
            Assert.assertEquals(loopSize * threadSize, i);
        }
    }

    @org.junit.Test
    public void doUnSafe() throws Exception {
        int threadSize = 10;
        int loopSize = 1000;

        Thread[] threads = new Thread[threadSize];
        final Integer[] sum = { 0 };
        for (int i = 0; i < threadSize; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i1 = 0; i1 < loopSize; i1++) {
                    SynchronizedDemo.doUnsafe(integer -> sum[0]++, finalI);
                }
            });
            thread.start();
            threads[i] = thread;
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(sum[0]);
    }

    @org.junit.Test
    public void doNonFinalLock() throws Exception {
        int threadSize = 10;
        int loopSize = 1000;

        Thread[] threads = new Thread[threadSize];
        final Integer[] sum = { 0 };
        for (int i = 0; i < threadSize; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i1 = 0; i1 < loopSize; i1++) {
                    SynchronizedDemo.doNonFinalLock(integer -> sum[0]++, finalI);
                }
            });
            thread.start();
            threads[i] = thread;
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(sum[0]);
    }

    @org.junit.Test
    public void doFinalLock() throws Exception {
        int threadSize = 10;
        int loopSize = 100;

        Thread[] threads = new Thread[threadSize];
        final Integer[] sum = { 0 };
        for (int i = 0; i < threadSize; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int finalI = i;
            Thread thread = new Thread(() -> {
                for (int i1 = 0; i1 < loopSize; i1++) {
                    SynchronizedDemo.doFinalLock(integer -> sum[0]++, finalI);
                }
            });
            thread.start();
            threads[i] = thread;
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(sum[0]);
        Assert.assertEquals(loopSize * threadSize, sum[0].intValue());

    }
}
