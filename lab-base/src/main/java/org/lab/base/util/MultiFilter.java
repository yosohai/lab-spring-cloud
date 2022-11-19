package org.lab.base.util;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MultiFilter   {
    public static void main(String[] args) {

        String S1 = "182";
        String S2 = "2278*1134*30";
        String S3 = "CHSM72N(DG)/F-BH";

        List<String> list = Arrays.asList(
                "超级BOM-CHSM72M-HC(166,2094*1038*35)",
                "超级BOM-CHSM72M-HC(166,2094*1038*30)",
                "超级BOM-CHSM54M-HC(182,1722*1134*30)",
                "超级BOM-CHSM54M(BL)-HC(182,1722*1134*30)",
                "超级BOM-CHSM72M-HC(182,2278*1134*35)",
                "超级BOM-CHSM72M-HC(182,2278*1134*30）",
                "超级BOM-CHSM60M-HC(210,2172*1303*35)",
                "超级BOM-CHSM66M-HC(210,2384*1303*35)",
                "超级BOM2.0玻璃-CHSM72M(DG)/F-BH(182,2278*1134*35)",
                "超级BOM2.0玻璃-CHSM72M(DG)/F-BH(182,2278*1134*30)",
                "超级BOM研发2.0玻璃-CHSM60M(DG)/F-BH(210,2172*1303*35)",
                "超级BOM研发2.0玻璃-CHSM66M(DG)/F-BH(210,2384*1303*35)",
                "超级BOM研发2.0玻璃-CHSM72N(DG)/F-BH(182,2278*1134*30)",
                "超级BOM研发2.0玻璃-CHSM72N(DG)/F-BH(182,2278 1134 35)");

        Optional<String> first = list.stream().filter(e -> e.equals("超级BOM-CHSM72M-HC(166,2094*1038*35)33")).findFirst();
        System.out.println("6666:" + first.orElse("kong"));
        List<String> collect = list.stream().filter(name -> {
            if (StrUtil.isBlank(S3)) {
                return true;
            }
            name = StrUtil.cleanBlank(name);
            name = name.replaceAll("（", "(")
                    .replaceAll("）", ")").replaceAll("，", ","); // 中文格式括号替换成英
            int leftLastIndex = name.lastIndexOf("("); // 获取最后一个括号位置
            int startIndex = name.indexOf("-"); // 获取最后一个括号位置
            String model = name.substring(startIndex + 1, leftLastIndex); // 型号
            if (StrUtil.isBlank(model)) {
                return false;
            }
            return S3.equals(model);
        }).filter(name -> {
            if (StrUtil.isBlank(S1)) {
                return true;
            }
            name = StrUtil.cleanBlank(name);
            name = name.replaceAll("（", "(")
                    .replaceAll("）", ")").replaceAll("，", ","); // 中文格式括号替换成英
            int leftLastIndex = name.lastIndexOf("(");
            int rightLastIndex = name.lastIndexOf(")");
            if (leftLastIndex == -1 || rightLastIndex == -1 || rightLastIndex - leftLastIndex + 1 < 0) {
                return false;
            }
//            System.out.println(name + ":" + leftLastIndex + ":" + rightLastIndex);
            String tmp = name.substring(leftLastIndex + 1, rightLastIndex);

            String s1 = ""; // 电池片尺寸
            String s2 = ""; // 组件尺寸
            if (!tmp.contains(",")) {
                return false;
            }
            String[] split = tmp.split(",");
            if (split.length != 2) {
                return false;
            }
            s1 = split[0]; // 电池片尺寸
            s2 = split[1]; // 组件尺寸
            if (StrUtil.isBlank(s1) || StrUtil.isBlank(s2)) {
                return false;
            }
            return S1.equals(s1);
        }).filter(name -> {

            if (StrUtil.isBlank(S2)) {
                return true;
            }
            name = StrUtil.cleanBlank(name);
            name = name.replaceAll("（", "(")
                    .replaceAll("）", ")").replaceAll("，", ","); // 中文格式括号替换成英
            int leftLastIndex = name.lastIndexOf("(");
            int rightLastIndex = name.lastIndexOf(")");
            if (leftLastIndex == -1 || rightLastIndex == -1 || rightLastIndex - leftLastIndex + 1 < 0) {
                return false;
            }
//            System.out.println(name + ":" + leftLastIndex + ":" + rightLastIndex);
            String tmp = name.substring(leftLastIndex + 1, rightLastIndex);

            String s1 = ""; // 电池片尺寸
            String s2 = ""; // 组件尺寸
            if (!tmp.contains(",")) {
                return false;
            }
            String[] split = tmp.split(",");
            if (split.length != 2) {
                return false;
            }
            s1 = split[0]; // 电池片尺寸
            s2 = split[1]; // 组件尺寸
            if (StrUtil.isBlank(s1) || StrUtil.isBlank(s2)) {
                return false;
            }
            return S2.equals(s2);
        }).collect(Collectors.toList());
        collect.stream().forEach(System.out::println);
    }
}
