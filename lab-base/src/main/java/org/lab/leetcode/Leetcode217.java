package org.lab.leetcode;

import java.util.HashSet;
import java.util.Set;

public class Leetcode217 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2};
        System.out.println(Solution117.duplication(nums));
    }
}

class Solution117 {
    public static boolean duplication(int[] nums) {
        int len = nums.length;
        Set<Integer> set = new HashSet<>(len);
        boolean result = false;
        for (int i = 0; i < len; i++) {
            if (!set.add(nums[i])) {
                result = true;
                break;
            }
        }
        return result;
    }
}