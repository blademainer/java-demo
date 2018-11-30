package com.xiongyingqi.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

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

        public void getGenericType(){
            Type mySuperClass = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
            Class clazz = (Class) type;
            System.out.println(clazz);
        }

    }

    public static void main(String[] args) {

        Foo<Map<String, String>> foo = new Foo<Map<String, String>>() {
        };
//        Type mySuperClass = foo.getClass().getGenericSuperclass();
//        Type type = ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
//        Class clazz = (Class<String>) type;
//        System.out.println(clazz);
        foo.getGenericType();
    }
}
