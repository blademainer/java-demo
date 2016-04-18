package com.xiongyingqi.concurrent;

import com.xiongyingqi.util.TimerHelper;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author xiongyingqi
 * @version 2016-03-03 18:02
 */
public class CallableAndFuture {

    private static void futureDemo() {
        Callable<Integer> callable = () -> {
            Thread.sleep(5000);
            return new Random().nextInt();
        };

        FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        TimerHelper.getTime();
        futureDemo();
        long time = TimerHelper.getTime();
        System.out.println("futureDemo time=====" + time);

    }
}
