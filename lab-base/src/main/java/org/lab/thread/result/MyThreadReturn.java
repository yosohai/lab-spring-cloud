package org.lab.thread.result;

public class MyThreadReturn implements Runnable {
    /**
     * 模拟线程执行完毕后主程序要获取的值
     */
    private String returnValue;

    @Override
    public void run() {
        System.out.println("线程执行......");
        /** 模拟IO*/
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程执行完毕......");
        returnValue = "hello world!!!";
    }

    public String getReturnValue() {
        return returnValue;
    }

    public static void main(String[] args) throws InterruptedException {
        MyThreadReturn myThreadReturn = new MyThreadReturn();
        Thread thread = new Thread(myThreadReturn);
        thread.start();
/*        while (StrUtil.isBlank(myThreadReturn.getReturnValue())) {
            Thread.sleep(1000);
        }*/
        thread.join();
        System.out.println(myThreadReturn.getReturnValue());
    }
}