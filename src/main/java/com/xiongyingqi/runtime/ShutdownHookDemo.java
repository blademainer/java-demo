package com.xiongyingqi.runtime;

/**
 * @author xiongyingqi
 * @since 17-4-10 下午8:02
 */
public class ShutdownHookDemo {
    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                System.out.println("running...");
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Shut down...");
            }
        });
    }
}
