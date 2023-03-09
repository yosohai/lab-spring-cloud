package org.lab.leetcode;

public class Leetcode53 {
    public static void main(String[] args) {
        int[] nums = new int[]{-2,1};
        System.out.println(Solution53.sumSubArray(nums));
    }
}

class Solution53 {
    public static int sumSubArray(int[] nums) {
        int len = nums.length;
        int max = 0;
        if (len == 1) return nums[0];
        for (int i = 0; i < len; i++) {
            int tmp = nums[i];
            for (int j = i + 1; j < len; j++) {
                tmp += nums[j];
                if (tmp > max) {
                    max = tmp;
                }
            }
        }
        return max;
    }
}