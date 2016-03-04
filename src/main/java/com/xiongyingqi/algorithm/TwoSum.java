package com.xiongyingqi.algorithm;

import java.util.HashMap;

/**
 * @author xiongyingqi
 * @version 2015-12-08 10:42
 */
public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int[] defaultResult = {0, 0};
        for (int i = 0; i < nums.length; i++) {
            if (map.get(target-nums[i]) != null ) {
                int[] result = {map.get(target-nums[i]) + 1, i + 1 };
                return result;
            }
            map.put(nums[i], i);
        }
        return defaultResult;
    }
}
