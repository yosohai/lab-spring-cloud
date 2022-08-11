package org.lab.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicIntegerTest {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        AtomicIntegerTest obj = new AtomicIntegerTest();
        IntStream.range(0, 10).forEach(i -> {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    obj.getAtomicInteger().getAndIncrement();
                    System.out.println(obj.getAtomicInteger().get());
                }
            }).start();
        });

    }

    public AtomicInteger getAtomicInteger() {
        return atomicInteger;
    }

    public void setAtomicInteger(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }
}
