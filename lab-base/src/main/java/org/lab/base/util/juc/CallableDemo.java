package org.lab.base.util.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new MyCallable());
        Thread callableDemo = new Thread(futureTask, "callableDemo");
        callableDemo.start();
        Object o = futureTask.get();
        System.out.println((Integer) o);
    }
}

class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 8888;
    }
}