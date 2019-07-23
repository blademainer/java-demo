package com.xiongyingqi.concurrent.exception;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * see https://imxylz.com/blog/2013/08/02/handling-the-uncaught-exception-of-java-thread-pool/
 */
public class ThreadPoolExceptionCatchDemo {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolExceptionCatchDemo.class);

    private ThreadPoolExecutor executorService = new ThreadPoolExecutor(10, 100, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new CustomizableThreadFactory("test-thread")) {
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            printException(r, t);
        }
    };

    private static void printException(Runnable r, Throwable t) {
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        if (t != null) {
            logger.error(t.getMessage(), t);
        }
    }

    public void run(){
        AtomicInteger atomicInteger = new AtomicInteger();
        while(atomicInteger.get() <= 10000){
            executorService.submit(() -> {
                int i = atomicInteger.incrementAndGet();
                System.out.println(i);
                if (i >= 1000) {
                    throw new RuntimeException("i greater than 1000! i:" + i);
                }
            });
        }
    }

    public static void main(String[] args) {
        ThreadPoolExceptionCatchDemo threadPoolExceptionCatchDemo = new ThreadPoolExceptionCatchDemo();
        threadPoolExceptionCatchDemo.run();
    }
}
