package org.lab.base.util.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrdinalPrint {
    final public static int LOOP_NUM = 40;

    public static void main(String[] args) {
        Print print = new Print();
        new Thread(() -> {
            for (int i = 0; i < LOOP_NUM; i++) {
                print.doPrintA();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < LOOP_NUM; i++) {
                print.doPrintB();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < LOOP_NUM; i++) {
                print.doPrintC();
            }
        }, "C").start();
    }
}

class Print {

    private final String tmp = "当前线程%s执行%s开始打印....";
    private int num = 1; // 1A 2B 3C
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    void doPrintA() {
        lock.lock();
        try {
            while (num != 1) {
                condition1.await();
            }
            System.out.println(String.format(tmp, Thread.currentThread().getName(), "doPrintA"));
            num = 2;
            condition2.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void doPrintB() {
        lock.lock();
        try {
            while (num != 2) {
                condition2.await();
            }
            System.out.println(String.format(tmp, Thread.currentThread().getName(), "doPrintB"));
            num = 3;
            condition3.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void doPrintC() {
        lock.lock();
        try {
            while (num != 3) {
                condition3.await();
            }
            System.out.println(String.format(tmp, Thread.currentThread().getName(), "doPrintC"));
            num = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}