package org.lab.base.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @Author: lzqing
 * @Description: 文件操作类
 * @Date: Created in 2021/6/6
 */
public class FileUtil {

    /**
     * 根据文件名读取文件内容
     *
     * @param fileName 字符串
     * @return 文件内容字符串
     * @throws java.io.IOException 读取过程中的异常信息
     */
    public static String read(String fileName) throws IOException {
        Resource resource = new ClassPathResource(fileName);
        try (InputStream is = resource.getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {
            String data = null;
            StringBuffer content = new StringBuffer();
            while ((data = br.readLine()) != null) {
                content.append(data);
            }
            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 读取文件内容
     *
     * @param file 文件对象
     * @return 文件内容字符串
     * @throws java.io.IOException 读取过程中的异常信息
     */
    public static String read(File file) throws IOException {
        try (InputStreamReader in = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(in)) {
            StringBuffer content = new StringBuffer();
            String str = "";
            while ((str = br.readLine()) != null) {
                content = content.append(str);
            }
            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
