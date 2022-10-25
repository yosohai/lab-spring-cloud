package org.lab.lambda;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ConsumerTest {
    public static void main(String[] args) {
        Consumer consumer = e -> System.out.println(e);
        Consumer andThen = e -> System.out.println(e + "-andThen");
        consumer.andThen(andThen).accept(6666);
        Stream.of("1", "2").forEach(consumer.andThen(andThen));
        System.out.println("-------------------");
        BiConsumer biConsumer = (c1, c2) -> {
            System.out.println(c1);
            System.out.println(c2);
        };
        BiConsumer biAndThen = (c1, c2) -> {
            System.out.println(c1+ "-andThen");
            System.out.println(c2+ "-andThen");
        };
        biConsumer.andThen(biAndThen).accept(2,3);
    }
}
