package com.xiongyingqi.concurrent.blocking.submit;

/**
 * @author xiongyingqi
 * @version 2016-05-12 20:31
 */
public interface Listener<T> {
    void noMoreDataEvent();
    void submittedData(T data);
}
