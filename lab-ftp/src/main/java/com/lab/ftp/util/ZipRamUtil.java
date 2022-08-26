package com.lab.ftp.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Securitit.
 * @note 基于内存以ZIP算法进行压缩和解压工具类.
 */
public class ZipRamUtil {
    public static void main(String[] args) throws Exception {
        Map<String, byte[]> fileBytesMap = null;

        fileBytesMap = new HashMap<String, byte[]>();
        // 设置文件列表.
        File dirFile = new File("C:/Users/Administrator/Downloads/个人文件/2020-07-13/files");
        for (File file : dirFile.listFiles()) {
            fileBytesMap.put(file.getName(), FileUtils.readFileToByteArray(file));
        }

        byte[] memoryBytes = ZipRamUtil.compressByZip(fileBytesMap);
        FileUtils.writeByteArrayToFile(new File("C:/Users/Administrator/Downloads/个人文件/2020-07-13/ram.zip"), memoryBytes);

        fileBytesMap = ZipRamUtil.decompressByZip(memoryBytes);
        System.out.println(fileBytesMap.size());
    }

    /**
     * 条目名称使用的默认字符.
     */
    public static String CHARSET_GBK = "GBK";

    /**
     * 使用ZIP算法进行压缩.
     *
     * @param sourceFileBytesMap 待压缩文件的Map集合.
     * @return 压缩后的ZIP文件字节数组.
     * @throws Exception 压缩过程中可能发生的异常，若发生异常，则返回的字节数组长度为0.
     */
    public static byte[] compressByZip(Map<String, byte[]> sourceFileBytesMap) throws Exception {
        // 变量定义.
        ZipEntry zipEntry = null;
        ZipOutputStream zipZos = null;
        ByteArrayOutputStream zipBaos = null;

        try {
            // 压缩文件变量初始化.
            zipBaos = new ByteArrayOutputStream();
            zipZos = new ZipOutputStream(zipBaos);
            // 将文件添加到ZIP条目中.
            if (null != sourceFileBytesMap && sourceFileBytesMap.size() > 0) {
                for (Map.Entry<String, byte[]> singleFile : sourceFileBytesMap.entrySet()) {
                    zipEntry = new ZipEntry(singleFile.getKey());
                    zipZos.putNextEntry(zipEntry);
                    zipZos.write(singleFile.getValue());
                }
            } else {
                zipBaos = new ByteArrayOutputStream();
            }
        } finally {
            if (null != zipBaos)
                zipBaos.close();
            if (null != zipZos)
                zipZos.close();
        }
        return zipBaos.toByteArray();
    }

    /**
     * 使用ZIP算法进行压缩.
     *
     * @param sourceFileBytesMap 待压缩文件的Map集合.
     * @return 压缩后的ZIP文件字节数组.
     * @throws Exception 压缩过程中可能发生的异常，若发生异常，则返回的字节数组长度为0.
     */
    public static byte[] compressByZipJdkLower7(Map<String, byte[]> sourceFileBytesMap) throws Exception {
        return compressByZipJdkLower7(sourceFileBytesMap, CHARSET_GBK);
    }

    /**
     * 使用ZIP算法进行压缩.
     *
     * @param sourceFileBytesMap 待压缩文件的Map集合.
     * @return 压缩后的ZIP文件字节数组.
     * @throws Exception 压缩过程中可能发生的异常，若发生异常，则返回的字节数组长度为0.
     */
    public static byte[] compressByZipJdkLower7(Map<String, byte[]> sourceFileBytesMap, String charset)
            throws Exception {
        // 变量定义.
        ByteArrayOutputStream zipBaos = null;
        org.apache.tools.zip.ZipOutputStream zipZos = null;

        try {
            // 压缩文件变量初始化.
            zipBaos = new ByteArrayOutputStream();
            zipZos = new org.apache.tools.zip.ZipOutputStream(zipBaos);
            // 将文件添加到ZIP条目中.
            if (null != sourceFileBytesMap && sourceFileBytesMap.size() > 0) {
                for (Map.Entry<String, byte[]> singleFile : sourceFileBytesMap.entrySet()) {
                    zipZos.putNextEntry(new org.apache.tools.zip.ZipEntry((singleFile.getKey())));
                    zipZos.write(singleFile.getValue());
                }
            } else {
                zipBaos = new ByteArrayOutputStream();
            }
        } finally {
            if (null != zipBaos)
                zipBaos.close();
            if (null != zipZos)
                zipZos.close();
        }
        return zipBaos.toByteArray();
    }

    /**
     * 使用ZIP算法进行解压.
     *
     * @param sourceZipFileBytes ZIP文件字节数组.
     * @return 解压后的文件Map集合.
     * @throws Exception 解压过程中可能发生的异常，若发生异常，返回Map集合长度为0.
     */
    public static Map<String, byte[]> decompressByZip(byte[] sourceZipFileBytes) throws Exception {
        // 变量定义.
        String zipEntryName = null;
        ZipEntry singleZipEntry = null;
        ZipInputStream sourceZipZis = null;
        BufferedInputStream sourceZipBis = null;
        ByteArrayInputStream sourceZipBais = null;
        Map<String, byte[]> targetFilesFolderMap = null;

        try {
            // 解压变量初始化.
            targetFilesFolderMap = new HashMap<String, byte[]>();
            sourceZipBais = new ByteArrayInputStream(sourceZipFileBytes);
            sourceZipBis = new BufferedInputStream(sourceZipBais);
            sourceZipZis = new ZipInputStream(sourceZipBis);
            // 条目解压缩至Map中.
            while ((singleZipEntry = sourceZipZis.getNextEntry()) != null) {
                zipEntryName = singleZipEntry.getName();
                targetFilesFolderMap.put(zipEntryName, IOUtils.toByteArray(sourceZipZis));
            }
        } finally {
            if (null != sourceZipZis)
                sourceZipZis.close();
            if (null != sourceZipBis)
                sourceZipBis.close();
            if (null != sourceZipBais)
                sourceZipBais.close();

        }
        return targetFilesFolderMap;
    }

}
