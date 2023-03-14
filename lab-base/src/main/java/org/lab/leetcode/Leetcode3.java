package org.lab.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Leetcode3 {

    public static void main(String[] args) {
//        System.out.println(lengthOfLongestSubstring("au"));
        String s = "a";
        System.out.println(s.substring(0, 1));
        System.out.println(lengthOfLongestSubstring1("dddddd"));
    }

    public static void print(int[] nums) {
        System.out.println(Arrays.stream(nums).sorted().boxed().collect(Collectors.toList()));
    }


    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        String[] arr = s.split("");
        int max = 0;

        Set<String> set = null;
        for (int i = 0, len = arr.length; i < len; i++) {
            set = new HashSet<>();
            set.add(arr[i]);
            for (int j = i + 1; j < len; j++) {
                if (!set.add(arr[j])) {
                    max = set.size() > max ? set.size() : max;
                    break;
                }
                set.add(arr[j]);
                max = set.size() > max ? set.size() : max;
            }
        }
        return set.size() > max ? set.size() : max;
    }

    public static int lengthOfLongestSubstring1(String s) {
        s = s == null ? "" : s;
        if ("".equals(s)) return 0;
        if (s.length() == 1) return 1;
        int max = 0;
        for (int i = 0, len = s.length(); i < len; i++) {
            for (int j = i + 1; j <= len; j++) {
                String subStr = s.substring(i, j);
                max = subStr.length() > max ? subStr.length() : max;
                if (subStr.length() > 1 && subStr.indexOf(s.charAt(i)) != -1) {
                    break;
                }
            }
        }
        return max;
    }
}
