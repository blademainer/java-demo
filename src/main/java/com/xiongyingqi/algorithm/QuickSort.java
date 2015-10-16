package com.xiongyingqi.algorithm;

import java.text.MessageFormat;
import java.util.Random;

/**
 * @author <a href="http://xiongyingqi.com">qi</a>
 * @version 2015-10-16 17:06
 */
public class QuickSort {
    private int innertI;
    int[] arr;

    private void swap(int x, int y) {
        String format = MessageFormat.format("{0} [{1}] <-> {2} [{3}]", x, arr[x], y, arr[y]);
        System.out.println(format);
        //        String format = MessageFormat.format("{0} <-> {1}", x, y);
        //        System.out.println(format);
        //        System.out.println("↓     ↓");
        //        String format2 = MessageFormat.format("{0} <-> {1}", arr[x], arr[y]);
        //        System.out.println(format2);
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

    private void quickSortRecursive(int start, int end) {
        if (start >= end)
            return;
        int mid = arr[end];
        int left = start, right = end - 1;
        while (left < right) {
            while (arr[left] < mid && left < right) {
                left++;
            }
            while (arr[right] >= mid && left < right) {
                right--;
            }
            printArray(arr);
            swap(left, right);
        }
        if (arr[left] >= arr[end]) {
            printArray(arr);
            swap(left, end);
        } else {
            left++;
        }
        System.out.println("---------------- recursive" + innertI++ + "----------------");
        quickSortRecursive(start, left - 1);
        printArray(arr);
        System.out.println("---------------- recursive" + innertI++ + "----------------");
        quickSortRecursive(left + 1, end);
        printArray(arr);
    }

    public void sort(int[] array) {
        arr = array;
        quickSortRecursive(0, arr.length - 1);
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 10;
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(size * 10);
        }
        printArray(array);
        QuickSort sort = new QuickSort();
        sort.sort(array);
        printArray(array);
    }
}
