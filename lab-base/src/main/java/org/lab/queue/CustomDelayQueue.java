package org.lab.queue;

import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 队列
 *
 * @author lzqing
 * @date 2022-07-07
 * @vsrsion 1.0
 **/
public class CustomDelayQueue {
    static DelayQueue<MyDelay> delayQueue = new DelayQueue<>();

    public static void main(String[] args) {
        producer(); // 调用生产者
        consumer(); // 调用消费者
    }

    public static void producer() {
        delayQueue.put(new MyDelay(1000L, "你好"));
        delayQueue.put(new MyDelay(3000L, "韩梅梅"));
    }

    public static void consumer() {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        while (!delayQueue.isEmpty()) {
            try {
                System.out.println(delayQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")));
        stopWatch.stop();
        System.out.println("结束执行时间：" +
                stopWatch.getTotalTimeNanos());

    }
}

class MyDelay<deadline> implements Delayed {

    // 延迟截止时间(单位毫秒)
    Long deadline = System.currentTimeMillis();

    private String msg;

    public MyDelay() {
    }

    public MyDelay(Long deadline, String msg) {
        this.deadline += deadline;
        this.msg = msg;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    // 获取剩余时间
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(deadline - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    // 队列元素排队依据
    @Override
    public int compareTo(Delayed o) {
        if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
            return 1;
        } else if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "MyDelay{" +
                "deadline=" + deadline +
                ", msg='" + msg + '\'' +
                '}';
    }
}