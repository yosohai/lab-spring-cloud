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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;

/**
 * @Author: lzqing
 * @Description: 文件操作类
 * @Date: Created in 2021/6/6
 */
public class FileUtil {


    public static void main(String[] args) throws IOException {
        // 如果文件不存在，则创建一个文件
        Path path = Paths.get("test.txt");
        Path pathBackup = Paths.get("test_bak.txt");
        Path pathLink = Paths.get("test.txt.link");
        Path pathDir = Paths.get("dir");

        // 已存在则删除
        Files.deleteIfExists(path);
        Files.deleteIfExists(pathBackup);
        Files.deleteIfExists(pathLink);
        Files.deleteIfExists(pathDir);

        // 创建文件写入内容
        Path file = Files.createFile(path);
        Files.write(path, "关注公众号：程序猿阿朗".getBytes());
        Files.write(path, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        Files.write(path, "欢迎加我微信：wn8398".getBytes(), StandardOpenOption.APPEND);
        System.out.println("创建文件：" + file.toString());
        // 创建文件链接
        pathLink = Files.createLink(pathLink, path);
        System.out.println("创建文件：" + pathLink.toString());

         // 创建目录
        Path directory = Files.createDirectory(pathDir);
        System.out.println("创建目录：" + directory.toString());

        // 文件复制
        Files.copy(path, pathBackup);
        System.out.println("复制文件: " + path + " --> " + pathBackup);

        // 读取文件
        List<String> lines = Files.readAllLines(pathBackup);
        for (String line : lines) {
            System.out.println("文件读取：" + line);
        }

        System.out.println("--------------------------华丽的分割线------------------------------------");

        path = Paths.get("/Users/darcy/git/jdk-feature/README.md");
        BasicFileAttributeView fileAttributeView = Files.getFileAttributeView(path, BasicFileAttributeView.class);
        BasicFileAttributes basicFileAttributes = fileAttributeView.readAttributes();
        FileTime creationTime = basicFileAttributes.creationTime();
        FileTime lastModifiedTime = basicFileAttributes.lastModifiedTime();
        FileTime lastAccessTime = basicFileAttributes.lastAccessTime();
        System.out.println("创建时间：" + creationTime);
        System.out.println("上次修改时间：" + lastModifiedTime);
        System.out.println("上次访问时间：" + lastAccessTime);

        boolean directory1 = basicFileAttributes.isDirectory();
        boolean regularFile = basicFileAttributes.isRegularFile();
        boolean symbolicLink = basicFileAttributes.isSymbolicLink();
        System.out.println("是否目录：" + directory1);
        System.out.println("是否普通文件：" + regularFile);
        System.out.println("是否符号链接：" + symbolicLink);

        long size = basicFileAttributes.size();
        System.out.println("文件大小：" + size);

        PosixFileAttributeView linuxFileAttributeView = Files.getFileAttributeView(path, PosixFileAttributeView.class);
        UserPrincipal owner = linuxFileAttributeView.getOwner();
        System.out.println("文件归属用户:" + owner.getName());


    }

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
