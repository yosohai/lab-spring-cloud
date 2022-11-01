package org.lab.collect.set;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetDemo {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("e");
        set.add("a");
        set.add("b");
        set.add("d");
        set.add("1");
        set.add("3");
        set.add("2");
//        set = set.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toSet());
        System.out.println(set);

        set = new LinkedHashSet<>();
        set.add("e");
        set.add("a");
        set.add("b");
        set.add("d");
        set.add("1");
        set.add("3");
        set.add("2");
        System.out.println(set);

        set = new TreeSet<>();
        set.add("e");
        set.add("a");
        set.add("b");
        set.add("d");
        set.add("1");
        set.add("3");
        set.add("2");
        System.out.println(set);
    }
}
