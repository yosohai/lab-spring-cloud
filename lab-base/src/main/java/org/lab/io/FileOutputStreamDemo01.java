package org.lab.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamDemo01 {
    public static void main(String[] args) {
        // 1.选择流：创建流对象
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(String.format("d:%sread.txt", File.separator)), true);
            // 2.准备数据源，把数据源转换成字节数组类型 //
            String msg = "春夜喜雨\n好雨知时节，当春乃发生。\n随风潜入夜，润物细无声。\n野径云俱黑，江船火独明。\n晓看红湿处，花丛锦官城。";
            byte[] data = msg.getBytes();
            // 3.通过流向文件当中写入数据
            fos.write(data, 0, data.length);
            // 4.刷新流
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) { // 5.关闭流
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}