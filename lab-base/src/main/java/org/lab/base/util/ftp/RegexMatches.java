package org.lab.base.util.ftp;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {

    public static void main(String args[]) {

        String a = "liu847yan064zhao370";
        a = "考/虑到房价快速的&&&$%$/%#$@#$@!#!#aabfooaabfo-_)加上/快递费(oabfoobkk/k-_()jdfj房价快速的减肥卡萨丁/";
//        String regEx = "[^0-9]";
//        regEx = "[^A-Za-z0-9-_\\( \\)-\\/\\\\\\\\-]";
        String regEx = "[^A-Za-z0-9-_\\( \\)-\\/\\\\\\\\-]"; // 提取出英文字母、数字、下划线、中划线、小括号、正斜杠(/)
        a = a.replaceAll(regEx, "");
        System.out.println("a:"+a);

//方法一（需要导入regex类）
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
        System.out.println(m.replaceAll(""));//847064370
//方法二、
        System.out.println(a.replaceAll(regEx, ""));//847064370


        a = a.replaceAll("（", "(")
                .replaceAll("）", ")"); // 中文格式括号替换成英文格式
        if (StrUtil.isNotBlank(a) || a.contains("(")) {
//            String cm = componentModel.split("\\(")[0];
            int indexOf = a.lastIndexOf("("); // 获取最后一个括号位置
            String cm = a.substring(0, indexOf);
            String regEx1 = "[^A-Za-z0-9-_\\( \\)-\\-]"; // 提取出英文字母、数字、下划线、中划线、小括号
            cm = cm.replaceAll(regEx1, "");
            System.out.println(cm);

        }

    }
}