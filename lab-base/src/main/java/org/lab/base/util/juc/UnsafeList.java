package org.lab.base.util.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UnsafeList {
    public static void main(String[] args) {
        List list = new ArrayList();
        List safeList = Collections.synchronizedList(list);
//        list = safeList;
//        list = new Vector();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                safeList.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(safeList);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "线程" + i).start();
        }
        System.out.println(1 << 4);
    }
}
