package org.lab.base.util;


import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class SplitDemo {

    public static void main(String[] args) {
        System.out.println("-----------------------leftPad-----------------------");
        System.out.println(StringUtils.leftPad("", 4, "0"));

        System.out.println("-----------------------split-----------------------");
        String fileName = "demo.rar";
        String[] parts = fileName.split("\\.");
        System.out.println(parts[0]);// demo
        System.out.println(parts[1]);// rar

        System.out.println("5555)9999".split("\\)")[0]);
        Arrays.stream("1,2,3".split(",")).forEach(System.out::println);
        System.out.println("-------------------replaceAll--------------------------");
        String str = "5/5/5/5/5";
        System.out.println(str.replaceAll("/", "\\\\")); // 5\5\5\5\5

        String filePicPath = "E:\\allFile\\modelFileExcelImage\\11.png";
        filePicPath = filePicPath.replaceAll("\\\\","/");
        System.out.println(filePicPath); // E:/allFile/modelFileExcelImage/11.png

        System.out.println("----------------indexOf------------------------------");
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
    /**
     * 中文标点符号替换成英文的
     *
     * @param str
     * @return
     */
    public static String punctuationMarksAlter(String str) {
        str = str.replaceAll("（", "(")
                .replaceAll("）", ")")
                .replaceAll("；", ";")
                .replaceAll("‘", "'")
                .replaceAll("’", "'")
                .replaceAll("“", "\"")
                .replaceAll("”", "\"")
                .replaceAll("：", ":")
                .replaceAll("？", "?")
                .replaceAll("【", "[")
                .replaceAll("】", "]")
                .replaceAll("！", "!")
                .replaceAll("．", ".")
                .replaceAll("，", ",");
        return str;
    }
}
