package org.lab.base.util.juc.delay;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class DelayQueueProducer implements Runnable {
    private BlockingQueue<DelayObject> queue;
    private Integer numberOfElementsToProduce;
    private Integer delayMilliseconds;

    public DelayQueueProducer() {
    }

    public DelayQueueProducer(BlockingQueue<DelayObject> queue, Integer numberOfElementsToProduce, Integer delayMilliseconds) {
        this.queue = queue;
        this.numberOfElementsToProduce = numberOfElementsToProduce;
        this.delayMilliseconds = delayMilliseconds;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToProduce; i++) {
            DelayObject obj = new DelayObject(UUID.randomUUID().toString(), delayMilliseconds);
            System.out.println("Put object: " + obj);
            try {
                queue.put(obj);
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
