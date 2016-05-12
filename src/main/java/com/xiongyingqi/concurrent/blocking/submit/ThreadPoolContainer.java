package com.xiongyingqi.concurrent.blocking.submit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiongyingqi
 * @version 2016-05-12 18:37
 */
public class ThreadPoolContainer {
    private static final Logger             logger          = LoggerFactory
            .getLogger(ThreadPoolContainer.class);
    private              ThreadPoolExecutor executorService = new ThreadPoolExecutor(10, 100, 0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
    public volatile      boolean            shutdown        = false;

    public void shutdown() {
        if (shutdown) {
            logger.error("Thread pool is shutting down!");
            return;
        }
        shutdown = true;
        List<Runnable> runnables = executorService.shutdownNow();
        logger.warn(
                "Shutting down thread pool! least thread size: {} and should be invoke continue!",
                runnables.size());
        for (Runnable runnable : runnables) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

    public void resize(int newSize) {
        if (shutdown) {
            return;
        }
        shutdown();
        executorService = new ThreadPoolExecutor(newSize, newSize, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        shutdown = false;
    }

    public ThreadPoolExecutor getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ThreadPoolExecutor executorService) {
        this.executorService = executorService;
    }
}
