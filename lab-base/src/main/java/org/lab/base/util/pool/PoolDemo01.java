package org.lab.base.util.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolDemo01 {
    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newSingleThreadExecutor(); // 单个线程的线程池
//        ExecutorService executorService = Executors.newFixedThreadPool(5); // 固定大小的线程池
        ExecutorService executorService = Executors.newCachedThreadPool(); // 可伸缩的 可大可小

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
