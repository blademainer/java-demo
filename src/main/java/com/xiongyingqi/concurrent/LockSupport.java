package com.xiongyingqi.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiongyingqi
 * @since 20170802
 */
public class LockSupport {
    private ReentrantLock[] reentrantLock;
    private int lockSize;
    private LockSupport(int lockSize){
        this.lockSize = lockSize;
        reentrantLock = new ReentrantLock[lockSize];
        for (int i = 0; i < lockSize; i++) {
            reentrantLock[i] = new ReentrantLock();
        }
    }

    public static LockSupport newInstance(int lockSize){
        return new LockSupport(lockSize);
    }

    public ReentrantLock getLock(Object object) {
        if (object == null) {
            throw new NullPointerException("Lock object is null!");
        }
        int hash = hash(object);
        ReentrantLock reentrantLock = lockForHash(hash);
        return reentrantLock;
    }

    private ReentrantLock lockForHash(int hash) {
        hash &= Integer.MAX_VALUE;
        int index = hash % lockSize;
        return reentrantLock[index];
    }

    private static int hash(Object object){
        int h = object.hashCode();

        // Spread bits to regularize both segment and index locations,
        // using variant of single-word Wang/Jenkins hash.
        h += (h <<  15) ^ 0xffffcd7d;
        h ^= (h >>> 10);
        h += (h <<   3);
        h ^= (h >>>  6);
        h += (h <<   2) + (h << 14);
        return h ^ (h >>> 16);
    }

}
