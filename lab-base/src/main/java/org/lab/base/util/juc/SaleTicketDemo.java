package org.lab.base.util.juc;

import lombok.Data;

import java.util.concurrent.TimeUnit;

public class SaleTicketDemo {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 1; i < 50; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket.sale();
            }
        }, "A").start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 1; i < 50; i++) {
                ticket.sale();
            }
        }, "B").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 1; i < 50; i++) {
                ticket.sale();
            }
        }, "C").start();
    }
}

@Data
class Ticket {
    private final String tmp = "%s卖出了第%s张票,剩余%s张票";
    private int num = 30;

    public synchronized void sale() {
        if (num > 0) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format(tmp, Thread.currentThread().getName(), num--, num));
        }
    }
}