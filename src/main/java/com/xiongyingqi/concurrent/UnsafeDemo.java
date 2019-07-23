//package com.xiongyingqi.concurrent;
//
//import sun.misc.Unsafe;
//
//import java.lang.reflect.Field;
//import java.util.concurrent.atomic.AtomicBoolean;
//
///**
// * @author xiongyingqi
// * @since 16-11-17 上午10:07
// */
//public class UnsafeDemo {
//
//    public static Unsafe getUnsafe() {
//        try {
//            Field unsafeField = AtomicBoolean.class.getDeclaredField("unsafe");
//            unsafeField.setAccessible(true);
//            Unsafe unsafe = (Unsafe) unsafeField.get(null);
//            return unsafe;
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
//
//    public static void main(String[] args) {
//        Unsafe unsafe = getUnsafe();
//        System.out.println(unsafe);
//    }
//}
