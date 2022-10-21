package org.lab.thread.fixed;

import java.util.concurrent.*;

public class FixedThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 1000; i++) {
            pool.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
    }
}
