package com.xiongyingqi.refrence;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author xiongyingqi
 * @version 2016-03-25 16:53
 */
public class ReferenceDemo {
    Entry entry = new Entry();
    private WeakReference<Entry> weakReference = new WeakReference<Entry>(entry);
    private SoftReference<Entry> softReference = new SoftReference<Entry>(entry);
    private PhantomReference<Entry> phantomReference = new PhantomReference<Entry>(entry, new ReferenceQueue<Entry>());

    public static void main(String[] args){

    }
}

