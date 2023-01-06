package com.lab.doc.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpUtil;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class POIExcelUtil {

    /**
     * 插入网络图片(单个单元格)
     *
     * @param workbook 文档对象
     * @param sheet    sheet页对象
     * @param url      网络路径
     * @param rowIndex 行号
     * @param colIndex 开始列号
     */
    public static void insertUrlImg(Workbook workbook, Sheet sheet, String url, int rowIndex, int colIndex) {
        insertUrlImg(workbook, sheet, url, rowIndex, rowIndex, colIndex, colIndex);
    }

    /**
     * 插入网络图片
     *
     * @param workbook      文档对象
     * @param sheet         sheet页对象
     * @param url           网络路径
     * @param beginRowIndex 开始行号
     * @param endRowIndex   结束行号
     * @param beginColIndex 开始列号
     * @param endColIndex   结束列号
     */
    public static void insertUrlImg(Workbook workbook, Sheet sheet, String url, int beginRowIndex, int endRowIndex
            , int beginColIndex, int endColIndex) {
        byte[] bytes = HttpUtil.downloadBytes(url);
        insertImg(workbook, sheet, bytes, beginRowIndex, endRowIndex, beginColIndex, endColIndex);
    }

    /**
     * 插入本地图片(单个单元格)
     *
     * @param workbook 文档对象
     * @param sheet    sheet页对象
     * @param url      本地路径
     * @param rowIndex 行号
     * @param colIndex 开始列号
     */
    public static void insertFileImg(Workbook workbook, Sheet sheet, String url, int rowIndex, int colIndex) throws Exception {
        insertFileImg(workbook, sheet, url, rowIndex, rowIndex, colIndex, colIndex);
    }

    /**
     * 插入本地图片
     *
     * @param workbook      文档对象
     * @param sheet         sheet页对象
     * @param url           本地路径
     * @param beginRowIndex 开始行号
     * @param endRowIndex   结束行号
     * @param beginColIndex 开始列号
     * @param endColIndex   结束列号
     */
    public static void insertFileImg(Workbook workbook, Sheet sheet, String url, int beginRowIndex, int endRowIndex
            , int beginColIndex, int endColIndex) throws Exception {
        //将本地路径的图片变成二进制流
        FileInputStream fileInputStream = new FileInputStream(new File(url));
        byte[] bytes = IoUtil.readBytes(fileInputStream);
        fileInputStream.close();
        //插入图片
        insertImg(workbook, sheet, bytes, beginRowIndex, endRowIndex, beginColIndex, endColIndex);
    }

    /**
     * 插入图片
     *
     * @param workbook      文档对象
     * @param sheet         sheet页对象
     * @param picture       图片二进制流数组
     * @param beginRowIndex 开始行号
     * @param endRowIndex   结束行号
     * @param beginColIndex 开始列号
     * @param endColIndex   结束列号
     */
    public static void insertImg(Workbook workbook, Sheet sheet, byte[] picture, int beginRowIndex, int endRowIndex
            , int beginColIndex, int endColIndex) {
        //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
        Drawing drawing = sheet.getDrawingPatriarch();
        if (drawing == null) {
            drawing = sheet.createDrawingPatriarch();
        }
        //anchor主要用于设置图片的属性
        ClientAnchor anchor = null;
        if (workbook instanceof HSSFWorkbook) {
            anchor = new HSSFClientAnchor(0, 0, 255, 255, (short) beginColIndex, beginRowIndex, (short) (endColIndex + 1), endRowIndex + 1);
        } else if (workbook instanceof XSSFWorkbook) {
            anchor = new XSSFClientAnchor(0, 0, 255, 255, (short) beginColIndex, beginRowIndex, (short) (endColIndex + 1), endRowIndex + 1);
        }
        //插入图片
        drawing.createPicture(anchor, workbook.addPicture(picture, Workbook.PICTURE_TYPE_JPEG));
    }
}
