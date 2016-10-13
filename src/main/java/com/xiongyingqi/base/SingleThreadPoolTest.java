package com.xiongyingqi.base;

import java.util.concurrent.Executors;

/**
 * -Xmx20m -Xms20m
 * @author xiongyingqi
 * @since 16-10-13 下午2:32
 */
public class SingleThreadPoolTest {
    public static void main(String[] args) {
        for (int i = 0; i < 2000; i++) {
            newSingleThreadPool();
        }
    }
    private static void newSingleThreadPool() {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = new byte[1024 * 1024 * 4];
                System.out.println(Thread.currentThread().getName());
            }
        });
    }
}
