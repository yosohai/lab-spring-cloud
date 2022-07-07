package org.lab.thread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Synchronous queue demo
 *
 * @author lzqing
 * @date 2022-07-05
 * @vsrsion 1.0
 **/
public class SynchronousQueueDemo {
    static SynchronousQueue queue = new SynchronousQueue();

    public static void main(String[] args) throws Exception {
        Thread thread0 = new Thread(() -> {
            IntStream.range(0, 20).forEach(i -> {
                try {
                    queue.put(i);
                    System.out.println("入队：" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }, "thread0");
        thread0.setDaemon(true);
        thread0.start();
        System.out.println("thread0:" + thread0.getName());

        Thread thread1 = new Thread(() -> {
            IntStream.range(0, 10).forEach(i -> {
                try {
                    System.out.println("出队：" + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }, "thread1");
        thread1.setDaemon(true);
        thread1.start();
        System.out.println("thread1:" + thread1.getName());
        System.out.println("Thread.currentThread():" + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(2);
        System.out.println(queue.take());


    }

}
