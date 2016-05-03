package com.xiongyingqi.reference;

/**
 * @author xiongyingqi
 * @version 2016-03-25 16:54
 */
public class Entry<T> {
    private String key;
    private T value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
