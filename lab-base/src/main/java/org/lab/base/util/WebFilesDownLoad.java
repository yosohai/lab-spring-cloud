package org.lab.base.util;

import lombok.Data;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class WebFilesDownLoad {
    public static void main(String[] args) {
        TestRunnable runnable1 = new TestRunnable("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1604162711877&di=482184c093d808b075495b12024b0877&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg", "1.jpg");
        TestRunnable runnable2 = new TestRunnable("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1604162711877&di=482184c093d808b075495b12024b0877&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg", "2.jpg");
        TestRunnable runnable3 = new TestRunnable("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1604162711877&di=482184c093d808b075495b12024b0877&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg", "3.jpg");
        new Thread(runnable1).start();
        new Thread(runnable2).start();
        new Thread(runnable3).start();
    }
}

@Data
class TestRunnable implements Runnable {

    private String strUrl;
    private String fileName;

    public TestRunnable(String strUrl, String fileName) {
        this.strUrl = strUrl;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.copyURLToFile(url, new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
