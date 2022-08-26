package org.lab.base.util.ftp;

import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;

public class FtpUtil {
    public static void main(String[] args) {
        Sftp sftp = JschUtil.createSftp("10.123.225.171", 2022, "plm", "plm@123!");

        if (!sftp.exist("/order")) {
            sftp.mkdir("/order");
        }
        //进入远程目录
        sftp.cd("/");
        //上传本地文件
        sftp.put("e:/test.png", "/order");
        //下载远程文件
        sftp.get("/bg.jpg", "e:/test2.jpg");
        //关闭连接
        sftp.close();
    }

}
