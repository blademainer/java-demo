package com.xiongyingqi.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiongyingqi
 * @since 16-12-16 下午6:47
 */
public class ThreadPoolDemo {
    private static final Logger               logger      = LoggerFactory
            .getLogger(ThreadPoolDemo.class);
    private              AtomicInteger        index       = new AtomicInteger();
    private final        ThreadLocal<Integer> threadIndex = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return index.incrementAndGet();
        }
    };

    ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 10, 10, TimeUnit.SECONDS,
                                                         new ArrayBlockingQueue<Runnable>(1000, true));
//    ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 10, 10, TimeUnit.SECONDS,
//                                                         new LinkedBlockingQueue<>());

    public void start() throws ExecutionException, InterruptedException {
        int size = 10000;
        List<Future<Boolean>> futures = new ArrayList<>(size);
        Thread.sleep(100);
        for (int i = 0; i < size; i++) {
            Future<Boolean> future = executor.submit(() -> {
                Thread.sleep(new Random().nextInt(1000));
                Integer integer = threadIndex.get();
                logger.info("index: {}", integer);
                return true;
            });
            futures.add(future);
        }
        for (Future<Boolean> future : futures) {
            future.get();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
        threadPoolDemo.start();
    }
}
