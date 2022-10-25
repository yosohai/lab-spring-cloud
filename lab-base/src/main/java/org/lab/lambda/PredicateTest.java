package org.lab.lambda;

import java.util.function.Predicate;
import java.util.stream.IntStream;

public class PredicateTest {
    public static void main(String[] args) {
        Predicate<String> p1 = e -> e.equals("");
        Predicate<String> p2 = e -> e.startsWith("X");
        Predicate<String> p3 = e -> e.contains("X");
        boolean x77777 = p3.or(p2).and(p1).test("X77777");
        System.out.println(x77777);


        IntStream.range(0,10).forEach(System.out::println);
    }
}
