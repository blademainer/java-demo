package com.xiongyingqi.concurrent.blocking.submit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiongyingqi
 * @version 2016-05-12 18:30
 */
public class BlockingTaskScheduler<T> {
    private static final Logger logger = LoggerFactory.getLogger(BlockingTaskScheduler.class);
    private final Producer<T> producer;
    private final Consumer<T> consumer;
    private ThreadPoolContainer threadPoolContainer = new ThreadPoolContainer();
    private List<Listener<T>>   listeners           = new ArrayList<Listener<T>>();

    public BlockingTaskScheduler(Producer<T> producer, Consumer<T> consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    public void start() {
        ThreadPoolExecutor executorService = threadPoolContainer.getExecutorService();
        int maximumPoolSize = executorService.getMaximumPoolSize();
        for (int i = 0; i < maximumPoolSize; i++) {
            submit();
        }
    }

    //    public void checkPool(){
    //        ThreadPoolExecutor executorService = threadPoolContainer.getExecutorService();
    //        int maximumPoolSize = executorService.getMaximumPoolSize();
    //        int activeCount = executorService.getActiveCount();
    //        while (activeCount < maximumPoolSize) {
    //
    //        }
    //    }

    Callable<Boolean> callable = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            if (!producer.hasMore()) {
                System.out.println("no more data to be consume...");
                notifyNoMoreData();
                return false;
            }
            T data = producer.produce();
            if (data == null) {
                return false;
            }
            notifySubmitted(data);
            consumer.consume(data);
            submit();
            return true;
        }
    };

    private Future<Boolean> submit() {
        Future<Boolean> submit = submit(callable);
        return submit;
    }
    private Future<Boolean> submit(Callable<Boolean> callable) {
        ThreadPoolExecutor executorService = threadPoolContainer.getExecutorService();

        Future<Boolean> submit = executorService.submit(callable);
        return submit;
    }

    public void addListener(Listener<T> listener) {
        listeners.add(listener);
    }

    private void notifySubmitted(T data) {
        if (listeners == null || listeners.isEmpty()) {
            return;
        }

        for (Listener<T> listener : listeners) {
            try {
                listener.submittedData(data);
            } catch (Exception e) {
                logger.error(
                        "Caught exception when notify submitted event to listener: " + listener
                                + " message: "
                                + e.getMessage(), e);
            }
        }
    }

    private void notifyNoMoreData() {
        if (listeners == null || listeners.isEmpty()) {
            return;
        }

        for (Listener<T> listener : listeners) {
            try {
                listener.noMoreDataEvent();
            } catch (Exception e) {
                logger.error(
                        "Caught exception when notify no more data event to listener: " + listener
                                + " message: " + e.getMessage(), e);
            }
        }
    }

    public static void main(String[] args) {
        final long size = 10000000;
        long start = System.currentTimeMillis();
        Producer<String> producer = new Producer<String>() {
            AtomicInteger i = new AtomicInteger();

            @Override
            public String produce() {
                int rs = i.getAndIncrement();
                return "" + rs;
            }

            public boolean _hasMore() {
                return i.get() < size;
            }

            @Override
            public boolean hasMore() {
                boolean b = _hasMore();
                return b;
            }
        };

        Consumer<String> consumer = data -> {
            //            try {
            //                Thread.sleep(1L);
            //            } catch (InterruptedException e) {
            //                e.printStackTrace();
            //            }
            //            System.out.println(data);
        };

        BlockingTaskScheduler<String> stringBlockingTaskScheduler = new BlockingTaskScheduler<>(
                producer, consumer);
        stringBlockingTaskScheduler.addListener(new Listener<String>() {
            @Override
            public void noMoreDataEvent() {
                long end = System.currentTimeMillis();
                long time = end - start;
                System.out.println("use time: " + time);
                stringBlockingTaskScheduler.threadPoolContainer.shutdown();
            }

            @Override
            public void submittedData(String data) {

            }
        });
        stringBlockingTaskScheduler.start();
    }
}
