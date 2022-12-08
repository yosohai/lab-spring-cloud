package org.lab.base.util.jol;

import lombok.Data;
import org.openjdk.jol.info.ClassLayout;

@Data
class BaseEntity  {

    private long id;

    private double amount;

    private int updateUserId;

    private float f;

    private char c;

    private byte b;

    private boolean bb;

    private short ss;

    private long[] list;

    private String s;

    private Long count;
}

public class ObjectHeaderTest {

    public static void main(String[] args) {
        BaseEntity baseEntity = new BaseEntity();
        String toPrintable = ClassLayout.parseInstance(baseEntity).toPrintable();
        System.out.println(toPrintable);
    }
}