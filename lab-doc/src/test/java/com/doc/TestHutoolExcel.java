package com.doc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.Test;

public class TestHutoolExcel {
    @Test
    public void writePicTest() {
        ExcelWriter writer = ExcelUtil.getWriter("D:\\1.xlsx");
        writer.setDefaultRowHeight(70);
        writer.setColumnWidth(-1, 30);

        //测试写入10个图片
        for (int i = 0; i < 10; i++) {
            //读取图片
            byte[] pictureData = FileUtil.readBytes("img/1.jpg");

            //写入图片
            writePic(writer, 0, i, pictureData, HSSFWorkbook.PICTURE_TYPE_JPEG);
        }

        writer.close();
    }

    /**
     * @param writer
     * @param x           单元格x轴坐标
     * @param y           单元格y轴坐标
     * @param pictureData 图片二进制数据
     * @param picType     图片格式
     */
    private void writePic(ExcelWriter writer, int x, int y, byte[] pictureData, int picType) {
        Sheet sheet = writer.getSheet();
        Drawing drawingPatriarch = sheet.createDrawingPatriarch();

        //设置图片单元格位置
        ClientAnchor anchor = drawingPatriarch.createAnchor(0, 0, 0, 0, x, y, x + 1, y + 1);
        //随单元格改变位置和大小
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);

        //添加图片
        int pictureIndex = sheet.getWorkbook().addPicture(pictureData, picType);
        drawingPatriarch.createPicture(anchor, pictureIndex);
    }
}

