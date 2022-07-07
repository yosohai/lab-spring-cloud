package org.lab.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

/**
 * Blocking Demo
 *
 * @author lzqing
 * @date 2022-07-05
 * @vsrsion 1.0
 **/
public class BlockingDemo {

    public static void main(String[] args) {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(5);
        new Thread(() -> {
            IntStream.range(0, 1000).forEach(e -> {
                try {
                    queue.put(String.format("%s*%s=%s", e, e, e * e));
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            });
        }).start();

        new Thread(() -> {
            IntStream.range(0, 10).forEach(e -> {
                try {
                    String take = (String) queue.take();
                    System.out.println(take);

                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            });
        }).start();
    }
}
