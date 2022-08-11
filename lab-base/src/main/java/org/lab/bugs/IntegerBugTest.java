package org.lab.bugs;

public class IntegerBugTest {
    public static void main(String[] args) {
        Integer i = 127;
        Integer b = 128;
        Integer c = 128;
        Integer d = 127;
        System.out.println(i == d); // true
        System.out.println(b == c); // false


        Integer aa = 128;
        Integer bb = 128;
        System.out.println(aa.hashCode() == bb.hashCode());
        // false 如果要比较的值，在-128—127之间，走的就是IntegerCache，那么比较结果==就是相等的
        // 如果要比较的值，不在-128—127之间，走的就是 new Integer(i)，==结果就不一样了
        System.out.println(aa == bb);
        System.out.println(aa.equals(bb));
    }
}
