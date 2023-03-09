package org.lab.leetcode;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Leetcode2 {

    public static void main(String[] args) {

    }

    public static void print(int[] nums) {
        System.out.println(Arrays.stream(nums).sorted().boxed().collect(Collectors.toList()));
    }
}

/*
 *
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution1 {
    private static int unit = 0;

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int v1, v2;
        ListNode node = new ListNode();
        v1 = l1 == null ? 0 : l1.val;
        v2 = l2 == null ? 0 : l2.val;
        int sum = v1 + v2 + unit;
        if (sum > 9) {
            node.val = sum % 10;
            unit = 1;
        } else {
            node.val = sum;
            unit = 0;
        }
        return node;
    }

}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}