package org.lab.base.util.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProductConsumeLock {
    final public static int LOOP_NUM = 40;

    public static void main(String[] args) {

        Resource2 resource = new Resource2();
        new Thread(() -> {
            try {
                for (int i = 0; i < LOOP_NUM; i++) {
                    resource.increment();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "大加法A").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < LOOP_NUM; i++) {
                    resource.decrement();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "大减法B").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < LOOP_NUM; i++) {
                    resource.increment();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "大加法C").start();


        new Thread(() -> {
            try {
                for (int i = 0; i < LOOP_NUM; i++) {
                    resource.decrement();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "大减法D").start();


        new Thread(() -> {
            try {
                for (int i = 0; i < LOOP_NUM; i++) {
                    resource.increment();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "大加法E").start();


        new Thread(() -> {
            try {
                for (int i = 0; i < LOOP_NUM; i++) {
                    resource.decrement();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "大减法F").start();
    }
}

// 等待 业务 通知
class Resource2 {
    private int num = 0;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            while (num != 0) {  //  if条件改成while防止虚假唤醒
                // 等待
                condition.await();
            }
            num++;
            System.out.println(String.format("%s-》:%s,唤醒减操作", Thread.currentThread().getName(), num));
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            while (num == 0) {
                condition.await();
            }
            num--;
            System.out.println(String.format("%s-》:%s,唤醒加操作", Thread.currentThread().getName(), num));
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}