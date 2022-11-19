package org.lab.bugs.tryresource;

import java.io.IOException;

/**
 * 释放资源
 * 关键异常丢失Bug演示
 * @author
 */
public class TryCatachFinallyThrowBug {

    /**
     * 异常处理
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        read();
    }

    public static void read() throws Exception {
        FileRead fileRead = null;
        try {
            fileRead = new FileRead();
            fileRead.read();
        } catch (Exception e) {
            throw e;
        } finally {
            if (fileRead != null) {
                try {
                    fileRead.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }

    }
}

class FileRead {

    public void read() throws Exception {
        throw new IOException("读取异常");
    }

    public void close() throws Exception {
        System.out.println("资源关闭");
        throw new IOException("关闭异常");
    }
}