package com.chint.similarity;

import cn.hutool.core.lang.Pair;
import com.chint.similarity.util.ListUtil;
import com.google.common.collect.Lists;

import java.util.List;

public class Test {

    public static void main1(String[] args) {
        String[] demo = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        int k = 0;
        for (int i = 0; i < demo.length; i++) {
            for (int j = i + 1; j < demo.length; j++) {
                k += 1;
                System.out.println(k + "," + demo[i] + "," + demo[j]);
            }
        }
    }


    public static void main(String[] args) {
        List<Pair<String, String>> pairs = ListUtil.concat(Lists.newArrayList("1", "2","3","4"));
        List<Pair<String, String>> pairs1 = ListUtil.concat(Lists.newArrayList("1", "2","3","4","5"));
        System.out.println(pairs);
        System.out.println(pairs1);

    }

}
