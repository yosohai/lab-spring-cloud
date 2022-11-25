package org.lab.base.util.juc;

public class ProductConsume {
    final public static int LOOP_NUM = 40;

    public static void main(String[] args) {

        Resource resource = new Resource();
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
        }, "大减法C").start();


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
        }, "大减法E").start();


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
class Resource {
    private int num = 0;

    public synchronized void increment() throws InterruptedException {
        while (num != 0) {  //  if条件改成while防止虚假唤醒
            // 等待
            wait();
        }
        num++;
        System.out.println(String.format("%s-》:%s,唤醒减操作", Thread.currentThread().getName(), num));
        // 通知
        this.notifyAll();

    }

    public synchronized void decrement() throws InterruptedException {
        while (num == 0) {
            wait();
        }
        num--;
        System.out.println(String.format("%s-》:%s,唤醒加操作", Thread.currentThread().getName(), num));
        this.notifyAll();
    }
}