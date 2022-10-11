package org.lab.base.util;

import cn.hutool.core.util.ZipUtil;

public class ZipDemo {

    public static void main(String[] args) {
//        ZipUtil.zip();

        String s = "ttt.arar";
        String[] split = s.split("\\.");
        System.out.println(split[0]);
        System.out.println(split[1]);

    }
}
