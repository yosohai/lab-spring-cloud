package org.lab.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayTest {

    private AtomicIntegerArray integerArray = new AtomicIntegerArray(10);

    public static void main(String[] args) {
        AtomicIntegerArrayTest obj = new AtomicIntegerArrayTest();
        AtomicIntegerArray integerArray = obj.getIntegerArray();
        System.out.println(integerArray.length());
        System.out.println(integerArray.get(0));

        System.out.println(integerArray.addAndGet(0, 6));
        System.out.println(integerArray.getAndAdd(1, 6));
        System.out.println(integerArray.get(1));

    }

    public AtomicIntegerArray getIntegerArray() {
        return integerArray;
    }

    public void setIntegerArray(AtomicIntegerArray integerArray) {
        this.integerArray = integerArray;
    }
}
