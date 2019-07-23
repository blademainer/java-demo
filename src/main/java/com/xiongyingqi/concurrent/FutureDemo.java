package com.xiongyingqi.concurrent;

import java.util.concurrent.*;

/**
 * @author <a href="http://xiongyingqi.com">xiongyingqi</a>
 * @version 2017-05-06 11:03
 */
public class FutureDemo implements Thread.UncaughtExceptionHandler {
  public static final int DEFAULT_TIMEOUT = 1000;
  // Thread pool
  private ExecutorService executorService = new FutureThreadPool(10, 10, DEFAULT_TIMEOUT,
      TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()
      , new ThreadFactory() {
    @Override
    public Thread newThread(Runnable r) {
      Thread thread = new Thread(r);
      thread.setDaemon(true);
      thread.setUncaughtExceptionHandler(FutureDemo.this);
      return thread;
    }

  }
  );

  public void run() {
    System.out.println("start...");
    try {
      Thread.sleep(DEFAULT_TIMEOUT * 2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("done...");
    throw new RuntimeException("error msg!");
  }

  public void push() {
    Runnable thread = FutureDemo.this::run;
//    FutureTask<Boolean> booleanFutureTask = new FutureTask<>(() -> {
//      System.out.println("start...");
//      Thread.sleep(DEFAULT_TIMEOUT * 2);
//      System.out.println("done...");
//      throw new Exception("error msg!");
//    });
    executorService.submit(thread);
    try {
//      Object o = submit.get(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
//      Object o = submit.get();
//      System.out.println("o============"+o);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
//    FutureDemo futureDemo = new FutureDemo();
//    futureDemo.push();


    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 500L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
          @Override
          public void uncaughtException(Thread t, Throwable e) {
            e.printStackTrace();
          }
        });
        return thread;
      }
    });
    while (true){
      threadPoolExecutor.submit(() -> {
        System.out.println("run...");
        throw new Exception("aaa");
      });
    }

//    Thread thread = new Thread(() -> {
//      System.out.println("start...");
//      throw new RuntimeException("eeee");
//    });
//    thread.setUncaughtExceptionHandler((t, e) -> {
//      e.printStackTrace();
//    });
//    thread.start();
  }

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    e.printStackTrace();
  }

  static class FutureThreadPool extends ThreadPoolExecutor {

    public FutureThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
      super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public FutureThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
      super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public FutureThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
      super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public FutureThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
      super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
      super.afterExecute(r, t);
      if (t == null && r instanceof Future<?>) {
        try {
          Object result = ((Future<?>) r).get();

        } catch (Exception ce) {
          t = ce;
//        } catch (ExecutionException ee) {
//          t = ee.getCause();
//        } catch (InterruptedException ie) {
//          Thread.currentThread().interrupt(); // ignore/reset
        }
      }
      if (t != null) {
        t.printStackTrace();
        System.out.println(t);
      }
    }
  }
}
