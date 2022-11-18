package org.lab.bugs.tryresource;

import java.io.IOException;

/**
 * 完美释放资源
 *
 *
 */
public class AutoCloseThrowFix {

    public static void main(String[] args) throws Exception {
        try (FileReadAutoClose fileRead = new FileReadAutoClose()) {
            fileRead.read();
        }
    }
}

class FileReadAutoClose implements AutoCloseable {

    public void read() throws Exception {
        System.out.println("资源读取");
        throw new IOException("读取异常");
    }

    @Override
    public void close() throws Exception {
        System.out.println("资源关闭");
        throw new IOException("关闭异常");
    }
}