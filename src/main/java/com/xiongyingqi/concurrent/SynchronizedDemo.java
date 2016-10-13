package com.xiongyingqi.concurrent;

import java.util.function.Function;

/**
 * @author xiongyingqi
 * @since 16-9-26 上午11:13
 */
public class SynchronizedDemo {
    private static       Object nonFinalLock = new Object();
    private static final Object finalLock    = new Object();

    public static <T, R> R doUnsafe(Function<T, R> function, T t) {
        R apply = function.apply(t);
        return apply;
    }

    public static <T, R> R doNonFinalLock(Function<T, R> function, T t) {
        synchronized (nonFinalLock) {
            R apply = function.apply(t);
            return apply;
        }
    }

    public static <T, R> R doFinalLock(Function<T, R> function, T t) {
        synchronized (finalLock) {
            R apply = function.apply(t);
            return apply;
        }
    }

    public static <T, R> R runByLock(Object lock, Function<T, R> function, T t) {
        synchronized (lock) {
            R apply = function.apply(t);
            return apply;
        }
    }

}
