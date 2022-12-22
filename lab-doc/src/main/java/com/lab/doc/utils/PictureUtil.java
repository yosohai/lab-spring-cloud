package com.lab.doc.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PictureUtil {

    /**
     * 生成图片缩略图
     */
    public static void photoSmaller(File sourceFile, File toFile) throws IOException {
        Thumbnails.of(sourceFile)
                .size(200, 150)//尺寸
                .outputQuality(0.8f)//缩略图质量
                .toFile(toFile);
    }


    /**
     * 生成图片缩略图
     */
    public static BufferedImage getThumbBufferedImage(File sourceFile) throws IOException {
        Thumbnails.Builder<File> builder = Thumbnails.of(sourceFile)
                .size(40, 30)//尺寸
                .outputQuality(0.8f);//缩略图质量
        return builder.asBufferedImage();
    }

}