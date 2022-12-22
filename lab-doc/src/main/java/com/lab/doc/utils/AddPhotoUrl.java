package com.lab.doc.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 
 public class AddPhotoUrl {
 
     public static void main(String[] args) {
         XSSFWorkbook workbook = new XSSFWorkbook();
         try {
             FileOutputStream out = new FileOutputStream("AddPhotoUrl.xlsx");
             XSSFSheet sheet = workbook.createSheet("1");
             XSSFCellStyle style = workbook.createCellStyle();
             XSSFFont font = workbook.createFont();
             font.setUnderline(XSSFFont.U_DOUBLE);
             font.setColor(IndexedColors.RED.getIndex());
             style.setFont(font);
             
             
             /**
              * cell中实现URL超链接
              */
             sheet.setColumnWidth(2, 4000);
             Row row = sheet.createRow(2);
             Cell cell = row.createCell(2);
             cell.setCellValue("Angel挤一挤的博客");
             cell.setCellStyle(style);
             

             /**
              * cell实现 插入图片
              */
             row = sheet.createRow(5);
             cell = row.createCell(5);
             row.setHeight((short) 1000);
             //画图的顶级管理器
             XSSFDrawing patriarch = sheet.createDrawingPatriarch();
             //为图片管理器配置参数  
             //参数1  第一个单元格中的x轴坐标
             //参数2 第一个单元格中的y轴坐标
             //参数3 第二个单元格中的x轴坐标
             //参数4 第二个单元格中的y轴坐标
             //参数5
             //参数6
             //参数7
             //参数8   
             XSSFClientAnchor anchor = new XSSFClientAnchor(100, 100, 255, 255, 13, 9, 14, 16);
             anchor.setAnchorType(AnchorType.DONT_MOVE_DO_RESIZE);
             
             
             ByteArrayOutputStream byteOutPut = new ByteArrayOutputStream();
             //读取到图片信息
             BufferedImage  bufferImage =ImageIO.read(new File("F:/酷我音乐.png"));
             //将图片写入到ByteArrayOutputStream中
             ImageIO.write(bufferImage, "png", byteOutPut);
             //参数1 代表图片的位置信息               参数2 代表图片来源
             patriarch.createPicture(anchor, workbook.addPicture(byteOutPut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG));

             workbook.write(out);
             out.close();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 
 }