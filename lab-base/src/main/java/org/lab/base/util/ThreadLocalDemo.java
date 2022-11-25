package org.lab.base.util;

public class ThreadLocalDemo {

    ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private String content;

    public String getContent() {
//        return content;
        return threadLocal.get();
    }

    public void setContent(String content) {
        this.content = content;
        threadLocal.set(content);
    }


    public static void main(String[] args) {
        ThreadLocalDemo demo = new ThreadLocalDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                demo.setContent(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + ":" + demo.getContent());
            }, "当前线程" + i).start();
        }

    }
}
