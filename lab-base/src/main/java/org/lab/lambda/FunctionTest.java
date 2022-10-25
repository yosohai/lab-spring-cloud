package org.lab.lambda;

import java.util.function.Function;

public class FunctionTest {

    public static void main(String[] args) {
        Function<Integer, Integer> f = s -> ++s;
        Function<Integer, Integer> g = s -> s * 2;
        System.out.println(f.andThen(g).apply(1));
        System.out.println(f.compose(g).apply(1));
    }


}
