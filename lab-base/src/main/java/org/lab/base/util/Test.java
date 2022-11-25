package org.lab.base.util;

import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String assemblyNumber = "uuuuu 9090888";
        assemblyNumber = Pattern.compile("[^0-9]").matcher(assemblyNumber).replaceAll("").trim();
        System.out.println(assemblyNumber);
    }
}
