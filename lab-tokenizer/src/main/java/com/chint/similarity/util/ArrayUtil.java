package com.chint.similarity.util;

import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

import static cn.hutool.core.lang.Console.print;

public class ArrayUtil {
    public static void main(String[] args) {
        Object[] arr = new Object[10];

        for (int i = 0; i < 3; i++) {
            arr[i] = "" + RandomUtil.randomString(1);
        }
        combination(arr, 2, null);
//        combination(list, 2);

        List<String> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            list.add("" + RandomUtil.randomString(1));
        }
//        combination(list, 2, "");
    }

    public static void combination(Object[] elements, int k, List<int[]> list) {

        int N = elements.length;
        if (k > N) {
            System.out.println("Invalid input, K > N");
            return;
        }

        // init combination index array
        int pointers[] = new int[k];


        int r = 0; // index for combination array
        int i = 0; // index for elements array

        while (r >= 0) {

            // forward step if i < (N + (r-K))
            if (i <= (N + (r - k))) {
                pointers[r] = i;

                // if combination array is full print and increment i;
                if (r == k - 1) {
//                    print(pointers);
                    list.add(pointers);
                    i++;
                } else {
                    // if combination is not full yet, select next element
                    i = pointers[r] + 1;
                    r++;
                }
            }

            // backward step
            else {
                r--;
                if (r >= 0)
                    i = pointers[r] + 1;

            }
        }
    }
}
