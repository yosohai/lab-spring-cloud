package org.lab.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class BiConsumerTest {
    public static void main(String[] args) {
        List<Integer> list11 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        list11.stream().forEach(e-> {
            System.out.println(e);
            if(e==6) ;
            return;
        });

        System.out.println("-----------------------------------");
        BiFunction<Integer, Integer, Long> add = (t, u) -> Long.valueOf(t + u);
        BiFunction<Integer, Integer, Long> sub = (t, u) -> Long.valueOf(t - u);
        BiFunction<Integer, Integer, Long> mul = (t, u) -> Long.valueOf(t * u);
        BiFunction<Integer, Integer, Long> div = (t, u) -> Long.valueOf(t / u);
        System.out.println(oper(add, 1, 2));
        System.out.println(add.apply(1, 2));
        System.out.println(sub.apply(11, 22));
        System.out.println(mul.apply(22, 22));
        System.out.println(div.apply(10, 2));

        String name = "*30*30*10*";
        if (name.startsWith("*")) {
//            name = name.replaceFirst("\\*", "%");
            name = name.replaceFirst("^\\*", "%");
            name = name.replaceFirst("\\*$", "%");
            System.out.println(name);
        }
        int serial = 1;
        for(int i=1;i<10;i++) {
            serial++;
            System.out.println(serial);
        }

    }

    public static Long oper(BiFunction<Integer, Integer, Long> fun, Integer t, Integer u) {
        return fun.apply(t, u);
    }
}
