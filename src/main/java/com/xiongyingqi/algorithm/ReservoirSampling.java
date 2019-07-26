package com.xiongyingqi.algorithm;

import java.util.Random;

/**
 * 蓄水池抽样算法
 * https://www.cnblogs.com/snowInPluto/p/5996269.html
 */
public class ReservoirSampling {
    private int[] pool; // 所有数据
    private final int N = 100000; // 数据规模
    private Random random = new Random();


    private int[] sampling(int K) {
        int[] result = new int[K];
        for (int i = 0; i < K; i++) { // 前 K 个元素直接放入数组中
            result[i] = pool[i];
        }

        for (int i = K; i < N; i++) { // K + 1 个元素开始进行概率采样
            int r = random.nextInt(i + 1);
            if (r < K) {
                result[r] = pool[i];
            }
        }

        return result;
    }


    public static void main(String[] args) {
        ReservoirSampling sampling = new ReservoirSampling();
        // 初始化
        sampling.pool = new int[sampling.N];
        for (int i = 0; i < sampling.N; i++) {
            sampling.pool[i] = i;
        }
        for (int i : sampling.sampling(100)) {
            System.out.println(i);
        }
    }
}
