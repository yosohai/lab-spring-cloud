package org.lab.base.util;

/**
 * @author lzqing
 * @date 2022-07-20
 * @vsrsion 1.0
 **/
public class OOP {
    private static final String DELIM = System.getProperty("file.separator"); // path separator for current OS

    public static void main(String[] args) {
        String str = "5/5/5/5/5";
        System.out.println(str.replaceAll("/", "\\\\"));

        String filePicPath = "E:\\allFile\\modelFileExcelImage\\11.png";
        filePicPath = filePicPath.replaceAll("\\\\","/");
        System.out.println(filePicPath);

        int serial = 1;

        System.out.println(String.valueOf(serial));
        serial++;
    }
}
