package com.xiongyingqi.cocurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author <a href="http://xiongyingqi.com">qi</a>
 * @version 2015-09-28 11:11
 */
public class ReadWriteLockDemo {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock          readLock      = readWriteLock.readLock();
    private final Lock          writeLock     = readWriteLock.writeLock();

}
