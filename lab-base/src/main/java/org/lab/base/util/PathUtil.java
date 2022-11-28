package org.lab.base.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtil {
    public static void main(String[] args) {
        Path path = Paths.get("/Users/darcy/java/");
        System.out.println("完整路径：" + path.toString());

        Path pathParent = path.getParent();
        System.out.println("父级路径：" + pathParent.toString());

        Path pathRoot = path.getRoot();
        System.out.println("根目录：" + pathRoot.toString());

        int pathNameCount = path.getNameCount();
        System.out.println("目录深度：" + pathNameCount);

        Path pathIndex3 = path.getName(2);
        System.out.println("第三级目录：" + pathIndex3);

        Path subPath = path.subpath(1, 3);
        System.out.println("第1级目录到第三级目录（包左不包右）：" + subPath.toString());

        // resolveSibling 从当前目录父目录开始拼接目录
        Path pathResolveSibling = path.resolveSibling("PathDemo.java");
        System.out.println("父目录开始拼接参数：" + pathResolveSibling.toString());

        // resolve 把当前路径当作父路径，参数作为子目录或者文件
        Path pathResolve = Paths.get("/Users/darcy/java/").resolve("PathDem.java");
        System.out.println("当前目录拼接后的目录：" + pathResolve.toString());

        // 参数路径相对于主体路径的相对路径
        Path path1 = Paths.get("/Users/darcy/");
        Path path2 = Paths.get("/Users/darcy/java/PathDemo.java");
        Path path3 = path1.relativize(path2);
        System.out.println("相对路径：" + path3.toString());

/* 输出结果
完整路径：/Users/darcy/java
父级路径：/Users/darcy
根目录：/
目录深度：3
第三级目录：java
第1级目录到第三级目录（包左不包右）：darcy/java
父目录开始拼接参数：/Users/darcy/PathDemo.java
当前目录拼接后的目录：/Users/darcy/java/PathDem.java
相对路径：java/PathDemo.java
*/

    }
}
