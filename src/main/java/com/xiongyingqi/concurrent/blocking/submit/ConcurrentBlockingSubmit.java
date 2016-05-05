package com.xiongyingqi.concurrent.blocking.submit;

import java.util.concurrent.*;

/**
 * 线程池内始终只有10个任务在执行，高效、低耗能～
 *
 * @author xiongyingqi
 * @version 2016-04-29 15:12
 */
public class ConcurrentBlockingSubmit<T> {
    private ThreadPoolExecutor    executorService = (ThreadPoolExecutor) Executors
            .newFixedThreadPool(10);
    private BlockingQueue<Future> completeQueue   = new LinkedBlockingQueue<Future>();
    Producer<T> producer;

    public ConcurrentBlockingSubmit(Producer<T> producer) {
        this.producer = producer;
    }

    public T getData() {
        return producer.produce();
    }

    public void start() throws ExecutionException, InterruptedException {
        int activeCount = executorService.getActiveCount();
        int corePoolSize = executorService.getCorePoolSize();
        while (activeCount < corePoolSize) {
            System.out.println("init...");
            submit();
            activeCount = executorService.getActiveCount();
            corePoolSize = executorService.getCorePoolSize();
        }
        startReceiveThread();
    }

    private void submit() {
        Future<Boolean> submit = executorService.submit(() -> {
            T data = getData();
            if (data == null) {
                return false;
            }
            System.out.println("executing data: " + data);
            //                Thread.sleep(1L);
            return true;
        });
        completeQueue.add(submit);
    }
    private void submit(T data) {
        Future<Boolean> submit = executorService.submit(() -> {
            System.out.println("executing data: " + data);
            //                Thread.sleep(1L);
            return true;
        });
        completeQueue.add(submit);
    }

    public void startReceiveThread() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    Future poll = completeQueue.poll();
                    try {
                        Object o = poll.get();
                        System.out.println(o);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    T data = getData();
                    submit(data);
                }

            }
        }.start();
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public Producer<T> getProducer() {
        return producer;
    }

    public void setProducer(Producer<T> producer) {
        this.producer = producer;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final long[] i = { 0 };
        Producer<String> producer = () -> "test" + ++i[0];
        ConcurrentBlockingSubmit<String> stringConcurrentBlockingSubmit = new ConcurrentBlockingSubmit<>(
                producer);
        stringConcurrentBlockingSubmit.start();

    }
}


