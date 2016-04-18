package com.xiongyingqi.concurrent;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author xiongyingqi
 * @version 2016-02-26 14:59
 */
public class HashMapDemo {
    public static void main(String[] args) throws InterruptedException {
        HashMap<String, String> map = new HashMap<>(2);
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                new Thread(() -> {
                    map.put(UUID.randomUUID().toString(), "i");
                }, "thread" + i).start();
            }
        });
        thread.start();
        thread.join();
    }
}
