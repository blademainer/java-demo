package com.xiongyingqi.cocurrent;

import com.xiongyingqi.util.TimerHelper;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author xiongyingqi
 * @version 2016-03-03 18:02
 */
public class CallableAndFuture {
    private static void completeServiceDemo() throws InterruptedException, ExecutionException {
//        ExecutorService threadPool = new ThreadPoolExecutor(40, 40, 2L, TimeUnit.SECONDS, new SynchronousQueue<>());
        ExecutorService threadPool = new ThreadPoolExecutor(40, 40, 2L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(threadPool);
        for (int i = 0; i < 10000; i++) {
            final int taskID = i;
            final int finalI = i;
            cs.submit(() -> {
                Thread.sleep(finalI % 10);
                return taskID;
            });
        }

        for (Future<Integer> take = cs.poll(10, TimeUnit.SECONDS); take != null; take = cs.poll(10, TimeUnit.SECONDS)){
            System.out.println(take.get());
        }
//        // 可能做一些事情
//        for (int i = 0; i < 5; i++) {
//            try {
//                System.out.println(cs.take().get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
    }

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

        TimerHelper.getTime();
        completeServiceDemo();
        long time2 = TimerHelper.getTime();
        System.out.println("completeServiceDemo time=====" + time2);

    }
}
