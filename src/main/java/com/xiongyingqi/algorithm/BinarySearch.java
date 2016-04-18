package com.xiongyingqi.algorithm;

import com.xiongyingqi.util.EntityHelper;

import java.text.MessageFormat;
import java.util.Random;

/**
 * @author <a href="http://xiongyingqi.com">qi</a>
 * @version 2015-11-02 15:30
 */
public class BinarySearch {
    public static int search(int[] array, int match) {
        System.out.println("array: " + EntityHelper.buildObjectToString(array) + ", match: " + match);
        if(array[0] > match || array[array.length - 1] < match){
            System.out.println("not found!");
            return -1;
        }
        int length = array.length;
        int middle = length / 2;
        int from = 0;
        int to = length - 1;
        while (true) {
            System.out.println(
                    MessageFormat.format("from: {0}, to: {1}, middle: {2}", from, to, middle));
            if (array[middle] == match) {
                return middle;
            } else if (match > array[middle]) {
                from = middle;
            } else if (match < array[middle]) {
                to = middle;
            }
            if(to - from == 1 && (array[from] < match && array[to] > match)){
                System.out.println("not found!");
                return -1;
            }
            middle = (from + to) / 2;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static int[] randomSeqedArray(int length, int maxInterval) {
        int[] array = new int[length];
        Random random = new Random();
        int next = 0;
        for (int i = 0; i < length; i++) {
            next = next + random.nextInt(maxInterval) + maxInterval;
            array[i] = next;
        }
        return array;
    }

    public static void main(String[] args) {
        int[] ints = randomSeqedArray(10000, 10);
        System.out.println(search(ints, 11211));
        System.out.println(search(new int[] { 0, 2, 3, 6, 8, 10, 12, 15, 66 }, 12));
        System.out.println(search(new int[] { 0, 2, 3, 6, 8, 10, 11, 15, 66 }, 12));
        System.out.println(search(new int[] { 0, 2, 3, 6, 8, 10, 11 }, 12));
        System.out.println(search(new int[] { 15, 66 }, 12));

    }
}
