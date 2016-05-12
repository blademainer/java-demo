package com.xiongyingqi.concurrent.blocking.submit;

/**
 * @author xiongyingqi
 * @version 2016-05-12 18:29
 */
public interface Consumer<T> {
    void consume(T data);
}
