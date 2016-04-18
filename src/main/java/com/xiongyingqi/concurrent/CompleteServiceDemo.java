package com.xiongyingqi.concurrent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author xiongyingqi
 * @version 2016-03-04 14:23
 */
public class CompleteServiceDemo {
    BlockingQueue<Runnable>    queue      = new LinkedBlockingQueue<Runnable>();
    ExecutorService            threadPool = new ThreadPoolExecutor(40, 40, 2L, TimeUnit.SECONDS,
            queue);
    CompletionService<Integer> cs         = new ExecutorCompletionService<Integer>(threadPool);


    private void completeServiceDemo() throws InterruptedException, ExecutionException {

        //        ExecutorService threadPool = new ThreadPoolExecutor(40, 40, 2L, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < 10000; i++) {
            final int taskID = i;
            final int finalI = i;
            cs.submit(() -> {
                Thread.sleep(finalI % 100);
                return taskID;
            });
        }

    }

    private void startMonitor() throws InterruptedException, ExecutionException {
        Thread thread = new Thread(() -> {
            try {
                for (Future<Integer> take = cs.poll(10, TimeUnit.SECONDS);
                     take != null; take = cs.poll(10, TimeUnit.SECONDS)) {
                    take.get();
                    //                    System.out.println(take.get());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }, "fetch thread");
        thread.start();

        Thread thread2 = new Thread(() -> {
            while (true) {
                int size = queue.size();
                System.out.println("size: " + size);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "monitor thread");
        thread2.start();

        List<Thread> threads = new ArrayList<>();
        threads.add(thread);
        threads.add(thread2);
        new Thread(() -> {
            while (true) {
                threads.forEach((x) -> {
                    //                    System.out.println(String.format("id: %d, name: %s, state: %s", x.getId(), x.getName(), x.getState()));
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        System.out.println(mapper.writeValueAsString(x));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void main(String[] args) {
        CompleteServiceDemo demo = new CompleteServiceDemo();
        try {
            demo.completeServiceDemo();
            demo.startMonitor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
