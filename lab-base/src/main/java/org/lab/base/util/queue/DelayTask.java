package org.lab.base.util.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟任务
 *
 * @author lzqing
 * @date 2022-07-21
 * @vsrsion 1.0
 **/
public class DelayTask implements Delayed {

    private long delay;
    private TimeUnit timeUnit;

    public DelayTask() {
    }

    public DelayTask(long delay, TimeUnit timeUnit) {
        this.delay = TimeUnit.NANOSECONDS.convert(delay, timeUnit);//统一转换成纳秒计数
        this.timeUnit = timeUnit;
    }

    public static void main(String[] args) {
        DelayQueue<DelayTask> dq = new DelayQueue();

        //入队四个元素，注意它们的延迟时间单位不一样。
        dq.offer(new DelayTask(5, TimeUnit.SECONDS));
        dq.offer(new DelayTask(2, TimeUnit.MINUTES));
        dq.offer(new DelayTask(700, TimeUnit.MILLISECONDS));
        dq.offer(new DelayTask(1000, TimeUnit.NANOSECONDS));

        while (dq.size() > 0) {
            try {
                System.out.println(dq.take());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delay - System.currentTimeMillis(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.getDelay(TimeUnit.NANOSECONDS) < o.getDelay(TimeUnit.NANOSECONDS)) //都换算成纳秒计算
            return -1;
        else if (this.getDelay(TimeUnit.NANOSECONDS) > o.getDelay(TimeUnit.NANOSECONDS)) //都换算成纳秒计算
            return 1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return "DelayTask{" +
                "delay=" + delay +
                ", timeUnit=" + timeUnit +
                '}';
    }
}
