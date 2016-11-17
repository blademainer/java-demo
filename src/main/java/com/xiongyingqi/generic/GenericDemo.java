package com.xiongyingqi.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author xiongyingqi
 * @since 16-11-11 下午4:21
 */
public class GenericDemo {
    public abstract static class Foo<T> {

        Class<T> type;

        public Foo() {
            this.type = (Class<T>) getClass();
        }

    }

    public static void main(String[] args) {

        Foo<String> foo = new Foo<String>() {
        };
        Type mySuperClass = foo.getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
        System.out.println(type);
    }
}
