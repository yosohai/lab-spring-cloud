package org.lab.thread;

import com.google.common.base.Stopwatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Thread001 {
    private volatile int a = 0; // 可见性 有序性 不能保证原子性

    public static void main(String[] args) throws Exception {
        Stopwatch started = Stopwatch.createStarted();
        Thread001 thread001 = new Thread001();
        final CountDownLatch latch = new CountDownLatch(10);
        IntStream.range(0, 10).forEach(i -> {
                    new Thread(() -> {
                        IntStream.range(0, 1000).forEach(j -> thread001.increase());
                        latch.countDown();
                    }).start();
                }
        );
        latch.await(3, TimeUnit.SECONDS);
/*        while (Thread.activeCount() > 1) {
            System.out.println(Thread.activeCount());
            Thread.currentThread().getThreadGroup().list();
            Thread.yield();
        }*/
        started.stop();
        System.out.println(String.format("耗时:【%s】,结果【%s】", started.elapsed(TimeUnit.MILLISECONDS), thread001.a));
    }

    public void increase() {
        synchronized (this) { // 可见性 有序性 原子性
            a++;
        }
    }

}
