package org.lab.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueTest {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue(8, false);

        /**
         *	      Throws exception	 Returns special value
         * Insert	add(e)	           offer(e) 插入
         * Remove	remove()	       poll()   删除
         * Examine	element()	       peek()   查找
         */
        System.out.println("开始add之前,可容纳元素数量：" + queue.remainingCapacity());
        queue.add("1");
        System.out.println("添加一个元素之后,可容纳元素数量：" + queue.remainingCapacity());
        queue.add("2");
        queue.add("3");
        queue.add("4");
        queue.add("5");
        queue.add("6");
        queue.add("7");
        queue.add("8");
        /*-----------------*/
//        System.out.println(queue.add("9")); // java.lang.IllegalStateException: Queue full
        List<String> list = new ArrayList<>();
        list.add("8");
        queue.removeIf(e -> list.contains(e));
        System.out.println(queue);
    }
}
