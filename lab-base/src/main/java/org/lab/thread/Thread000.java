package org.lab.thread;

import java.util.stream.IntStream;

public class Thread000 {
    public int a = 0;

    public static void main(String[] args) {
        final Thread000 test = new Thread000();
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    for (int j = 0; j < 1000; j++)
                        test.increase();
                }

                ;
            }.start();
        }

        while (Thread.activeCount() > 1) {
            // 保证前面的线程都执行完
            Thread.yield();
        }
        System.out.println(test.a);
        int size = 10;
        IntStream.range(0, size).forEach(e -> System.out.println(e));
    }

    public void increase() {
        a++;
    }
}