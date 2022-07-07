package org.lab.base.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 基础测试
 *
 * @author lzqing
 * @date 2022-06-28
 * @vsrsion 1.0
 **/
public class Test {
    public static void main(String[] args) {
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
