package org.lab.base.util;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {
    public static void main(String[] args) throws Exception {
//        AtomicInteger atomicInteger = new AtomicInteger();
//        atomicInteger.getAndAdd(1);
//        System.out.println(atomicInteger.get());
//        Object obj = new Object();
//        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        Thread thread = new Thread(() -> {
            System.out.println("thread 1 args = " + Arrays.deepToString(args));
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        new Thread(() -> {
            try {
                System.out.println("thread 2 begin = " + Arrays.deepToString(args));
                thread.join();
                System.out.println("thread 2 end = " + Arrays.deepToString(args));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
