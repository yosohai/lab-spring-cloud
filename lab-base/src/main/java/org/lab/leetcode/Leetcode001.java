package org.lab.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Leetcode001 {

    public static void main(String[] args) {
        int[] ints = Solution.twoSum(new int[]{2, 7, 11, 15}, 18);
        print(ints);
        ints = Solution.twoSum1(new int[]{2, 7, 11, 15}, 18);
        print(ints);
    }

    public static void print(int[] nums) {
        System.out.println(Arrays.stream(nums).boxed().collect(Collectors.toList()));
    }
}

class Solution {
    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0, len = nums.length; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[i] + nums[j] == target) return new int[]{i, j};
            }
        }
        return null;
    }

    public static int[] twoSum1(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0, len = nums.length; i < len; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0, len = nums.length; i < len; i++) {
            int left = target - nums[i];
            if (map.containsKey(left)) {
                return new int[]{i, map.get(left).intValue()};
            }
        }
        return null;
    }
}