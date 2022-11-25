package org.lab.base.util.juc.delay;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class DelayQueueConsumer implements Runnable {

    public AtomicInteger numberOfConsumedElements = new AtomicInteger();
    private BlockingQueue<DelayObject> queue;
    private Integer numberOfElementsToTake;

    public DelayQueueConsumer() {
    }

    public DelayQueueConsumer(BlockingQueue<DelayObject> queue, Integer numberOfConsumedElements) {
        this.queue = queue;
        this.numberOfElementsToTake = numberOfConsumedElements;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToTake; i++) {
            try {
                DelayObject obj = queue.take();
                numberOfConsumedElements.incrementAndGet();
                System.out.println("Consumer take: " + obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
