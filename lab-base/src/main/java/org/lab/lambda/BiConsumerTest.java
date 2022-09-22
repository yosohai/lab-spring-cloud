package org.lab.lambda;

import java.util.function.BiConsumer;

public class BiConsumerTest {
    public static void main(String[] args) {
        BiConsumer<String, String> biConsumer = (t, u) -> {
            System.out.println(t + u);
            return;
        };
    }
}
