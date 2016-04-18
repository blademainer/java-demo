package com.xiongyingqi.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiongyingqi
 * @version 2016-03-21 14:36
 */
public class DoubleCheck {
    private Map<String, String> instanceMap = new HashMap<String, String>();
    private AtomicInteger       size        = new AtomicInteger();

    public String get(String key) {
        String s = instanceMap.get(key);
        if (s == null) {
            synchronized (DoubleCheck.class) {
                if (instanceMap.get(key) == null) {
                    System.out.println("init...." + key);
                    s = key;
                    instanceMap.put(key, key);
                    int i = size.incrementAndGet();
                    System.out.println("init size ====" + i); // size should by the max key size!
                    return s;
                }
            }
        }
        return s;
    }

    public static void main(String[] args) {
        DoubleCheck doubleCheck = new DoubleCheck();
        for (int i = 0; i < 10000; i++) {
            final int finalI = i;
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String s = doubleCheck.get((finalI % 100) + "");
                }
            }.start();
        }
    }

}
