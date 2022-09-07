package org.lab.base.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import com.google.common.collect.Maps;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.extern.slf4j.Slf4j;
import org.lab.enums.PartIbaPropertyEnum;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 基础测试
 *
 * @author lzqing
 * @date 2022-06-28
 * @vsrsion 1.0
 **/
@Slf4j
public class Test {
    public static void main(String[] args) {
        String str66 = "……^1dsf  の  adS -_  DFASFSADF阿德斯防守对方asdfsadf37《？：？@%#￥%#￥%@#$%#@$%^><?1234";
        String regEx="/^[A-Za-z0-9_\\( \\)\\-\\#]+$";
        regEx="^[a-zA-Z][a-zA-Z0-9_]$";
        regEx="/[a-z]+/gi";

        Pattern   p   =   Pattern.compile(regEx);
        Matcher   mm   =   p.matcher(str66);
        StringBuffer sb = new StringBuffer();
        while(mm.find()){
            sb.append(mm.group());
        }
        System.out.println(sb);

        String resultExtractMulti = ReUtil.extractMulti("^[A-Za-z0-9]+$", "gsfsdfsd就是快递费加快速度", "$1-$2");
        System.out.println(resultExtractMulti);



        String test = "jkfsdjfks-/x(dd)（地方就是打开附件是打开)";

        String test1 = "jkfsdjfks-/x(dd)(地方就是打开附件是打开)";


        Pattern pattern = Pattern.compile("/^[\\(（].*[\\)_a-zA-Z0-9]+$");
        Matcher m = pattern.matcher(test);
        String str99 = "";
        if (m.find()) {
            str99 = m.group(1);
        }
        System.out.println(str99);


        int i1 = punctuationMarksAlter(test).lastIndexOf("(");

        String substring = test.substring(0, i1);
        System.out.println(substring);

        Map<String, String> map = new HashMap<>();
        map.put("1", "11");
        map.put("2", "22");
        map.put("3", "33");
        map.put("4", "44");
        Collection<String> values = map.values();
        List<String> list33 = new ArrayList<>(values);

        list33.stream().forEach(System.out::println);

        for (int i = 0; i < list33.size(); i++) {
            System.out.println(list33.get(i));
        }

        try {
            int a = 1 / 0;
        } catch (Exception e) {
            log.error("错错了", e);
        }
        String str1 = "Default/订单库/配置项/某年/某月/";
        String[] split = str1.split("/");
        System.out.println(split);
        Arrays.stream(split).forEach(System.out::println);

/*
        str1.lastIndexOf("/");
        String substring = str1.substring(str1.lastIndexOf("/")+1);
        System.out.println(substring);
*/
        System.out.println(str1.indexOf("/", 0));
        int index = 0;
        while ((index = str1.indexOf("/", index)) != -1) {
            if (index != 0) {
                System.out.println(str1.substring(0, index));
            }
            index = index + "/".length();
        }


        System.out.println(PartIbaPropertyEnum.APPEARANCE.name());
        System.out.println(PartIbaPropertyEnum.APPEARANCE.getDesc());
        LocalDate now = LocalDate.now();
        System.out.println(now.getYear());
        System.out.println(now.getMonthValue());

        String project = "demandOrder.getProject()"; // 订单名称
        project += DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
        System.out.println(project);
        String str = "CP1/CP2/CP3";
        int i = str.lastIndexOf("/");
        System.out.println(str.substring(i + 1));
        System.out.println(str.substring(0, i));


        Person p1 = new Person();
        p1.setName("张三");
        p1.setCode("10001");
        p1.setAddress("大明湖");
        p1.setAge((short) 99);

        HashMap<String, Person> hashMap = new HashMap<>();
        hashMap.put("p1", p1);

        String str2 = "Hello world";

        List<String> list = Arrays.asList("hello", "world");
        final List<String[]> collect = list.stream().map(e -> e.split("")).distinct().collect(Collectors.toList());
        System.out.println(collect);

        List<String> list2 = Arrays.asList("hello", "world");
        List<Stream<String>> collect2 = list2.stream().map(e -> e.split(""))
                .map(Arrays::stream).
                        distinct().collect(Collectors.toList());
        System.out.println(collect2);

        List<String> list3 = Arrays.asList("hello", "world");
        List<String> collect3 = list3.stream().map(e -> e.split("")).flatMap(Arrays::stream).collect(Collectors.toList());
        System.out.println(collect3);

        List<Integer> list11 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> list22 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<Integer> collect4 = list11.stream().filter(e -> e % 2 == 0).collect(Collectors.toList());
        System.out.println(collect4);

        final List<int[]> collect1 = list11.stream().filter(e -> e % 2 == 0).flatMap(e -> list22.stream().filter(e1 -> e1 % 2 == 1).map(e2 -> new int[]{e, e2})).collect(Collectors.toList());
        collect1.stream().forEach(e -> Arrays.stream(e).forEach(System.out::print));

        DelayQueue<Person> delayQueue = new DelayQueue<Person>();
        String articleName = "55555(fsdfsdfsd)";
        boolean contains = articleName.indexOf("(") != -1;
        boolean contains2 = articleName.contains("(");
        System.out.println("\n" + articleName.split("\\(")[0]);
        System.out.println(contains);
        System.out.println(contains2);
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



class Person implements Delayed {
    private String name;
    private String code;
    private Short age;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(code, person.code) && Objects.equals(age, person.age) && Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code, age, address);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return 0;
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
