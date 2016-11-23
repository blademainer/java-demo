package com.xiongyingqi.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author xiongyingqi
 * @since 16-11-17 下午9:56
 */
public class ScheduleTaskDemo implements Runnable {

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println("Sleeping...");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Throwing...");
        throw new RuntimeException("Fuck!");
    }

    public static void main(String[] args) {
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new ScheduleTaskDemo(), 0, 1,
                                                                   TimeUnit.MILLISECONDS);
    }
}
