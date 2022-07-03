package org.lab.thread;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Thread001 {

    private volatile int a = 0;

    public void increase() {
        synchronized (this) {
            a++;
        }
    }

    public static void main(String[] args) throws Exception {
        Thread001 thread001 = new Thread001();
        IntStream.range(0, 10).forEach(i -> new Thread(() -> {
            System.out.println(i);
            IntStream.range(0, 1000).forEach(j -> thread001.increase());
        }).start());
        System.out.println(thread001.a);
        while (Thread.activeCount() > 1) {
            System.out.println(Thread.activeCount());
            Thread.yield();
        }
        System.out.println("end");
        System.out.println(thread001.a);
    }

}
