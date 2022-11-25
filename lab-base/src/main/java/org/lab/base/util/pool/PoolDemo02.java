package org.lab.base.util.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolDemo02 {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                Runtime.getRuntime().availableProcessors(),
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(300),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()

        );

        try {
            for (int i = 1; i <= 1000; i++) {
                executorService.execute(() -> {
                    System.out.println(String.format("%s开始执行", Thread.currentThread().getName()));
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }
}
