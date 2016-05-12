package com.xiongyingqi.concurrent.blocking.submit;

/**
 * @author xiongyingqi
 * @version 2016-04-29 15:18
 */
public interface Producer<T> {
    T produce();
    boolean hasMore();
}
