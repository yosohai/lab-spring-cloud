package org.lab.base.util;

import cn.hutool.core.util.StrUtil;

public class O0930 {
    public static void main(String[] args) {
        String s = "000超级BOM-000CHSM72M-HC(166,2094*1038*35)";
        int leftLastIndex = s.lastIndexOf("("); // 获取最后一个括号位置
        int rightLastIndex = s.lastIndexOf(")"); // 获取最后一个括号位置
        String tmp = s.substring(leftLastIndex + 1, rightLastIndex); // 型号
        System.out.println(tmp);
        String name = "000超级BOM-000CHSM72M-HC(166,2094*1038*35)";
        name = StrUtil.cleanBlank(name);
        int startIndex = name.indexOf("-"); // 获取最后一个括号位置
        String model = name.substring(startIndex + 1, leftLastIndex); // 型号
        System.out.println(model);
    }
}
