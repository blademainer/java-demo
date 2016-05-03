package com.xiongyingqi.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * @author xiongyingqi
 * @version 2016-03-25 16:53
 */
public class ReferenceDemo {
    Entry entry = new Entry();
    private WeakReference<Entry>                                  weakReference    = new WeakReference<Entry>(
            entry);
    private SoftReference<Entry>                                  softReference    = new SoftReference<Entry>(
            entry);
    private PhantomReference<Entry>                               phantomReference = new PhantomReference<Entry>(
            entry,
            new ReferenceQueue<Entry>());
    private HashMap<WeakReference<String>, WeakReference<String>> cacheMap         = new HashMap<WeakReference<String>, WeakReference<String>>();

    public void setCache(String key, String value) {
        WeakReference<String> weakReference = new KeyCache(value);
        KeyCache keyRef = new KeyCache(key);
        cacheMap.put(keyRef, weakReference);
    }

    public String get(String key) {
        KeyCache keyRef = new KeyCache(key);
        WeakReference<String> entryWeakReference = cacheMap.get(keyRef);
        if (entryWeakReference == null) {
            cacheMap.remove(keyRef);
            return null;
        }
        String value = entryWeakReference.get();
        if (value != null) {
            return value;
        } else {
            cacheMap.remove(keyRef);
            return null;
        }
    }

    public static void main(String[] args) {
        // vm option:
        // -Xms1M -Xmx1M
        ReferenceDemo referenceDemo = new ReferenceDemo();
        for (int i = 0; i < 1000; i++) {
            referenceDemo.setCache(i + "", i + "");
        }
        for (int i = 0; i < 1000; i++) {
            System.out.println(i + "====" + referenceDemo.get(i + ""));
        }
        System.out.println(referenceDemo.cacheMap);
    }
}

