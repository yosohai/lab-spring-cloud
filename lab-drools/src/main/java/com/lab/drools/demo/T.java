package com.lab.drools.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class T {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list = null;
        list.stream().forEach(System.out::println);
        Optional<List<String>> o = Optional.ofNullable(list);
        list = o.orElse(new ArrayList<>());
        for (String str : list) {
            System.out.println(str);
        }

    }

}
