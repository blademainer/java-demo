package com.xiongyingqi.concurrent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiongyingqi
 * @since 17-2-28 下午6:41
 */
public class HashMapConcurrentTest implements Runnable {
    private static final Map<String, String> MAP = new HashMap<>();
    private static int i;

    public void run() {
        MAP.put(i++ + "", i++ + "");
        System.out.println(Thread.currentThread() + "");
    }

    public static void main(String[] args) {
        HashMapConcurrentTest hashMapConcurrentTest = new HashMapConcurrentTest();
        new Thread(() -> {
            for (; ; ) {
                hashMapConcurrentTest.run();
            }
        }).start();
        new Thread(() -> {
            for (; ; ) {
                hashMapConcurrentTest.run();
            }
        }).start();
    }
}
