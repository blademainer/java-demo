package com.xiongyingqi.concurrent;

/**
 * @author xiongyingqi
 * @version 2016-04-15 16:18
 */
public class VolatileDemo {
    private volatile boolean isWriting = false;

    public void write() {
        while (isWriting) {
            System.out.println("wait for another thread writing....");
        }
        isWriting = true;
        try {
            System.out.println("writing....");
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isWriting = false;
    }

    public void read() {
        while (isWriting) {
            System.out.println("read.... wait for another thread writing....");
        }
        System.out.println("reading....");
    }

    public static void main(String[] args) {
        VolatileDemo demo = new VolatileDemo();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                demo.read();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                demo.write();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                demo.write();
            }
        }).start();

    }
}
