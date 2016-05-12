package com.xiongyingqi.util;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiongyingqi
 * @version 2016-05-11 17:53
 */
public class RandomDemo {

    public int nextInt() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final Random random = new Random();
        int i = random.nextInt();
        return i;
    }

    public static void main(String[] args) {
        RandomDemo randomDemo = new RandomDemo();
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
//        HashSet<Integer> integers = new HashSet<>();
        int size = 10000;
        for (int i = 0; i < size; i++) {
            new Thread(() -> {
                int j = randomDemo.nextInt();
//                integers.add(j);
                Integer integer = concurrentHashMap.get(j);
                if (integer == null) {
                    concurrentHashMap.put(j, 1);
                } else {
                    concurrentHashMap.put(j, integer + 1);
                }
                //                System.out.println(j);
            }).start();
        }
        System.out.println(concurrentHashMap.size()); // result size is less than size
    }
}
