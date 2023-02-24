package com.chint;


import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ppttopdf {

    public static void main(String[] args) {
        //输入ppt的路径
        File pptFile = new File("D:\\file\\ppt1.pptx");
        List<File> files = pptx2Png(pptFile);
        png2Pdf(files,"D:\\file\\ppt1.pdf");
    }

    public static   List<File> ppt2Png(File pptFile) {
        List<File> pngFileList = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        FileInputStream is = null;
        // 将ppt文件转换成每一帧的图片
        HSLFSlideShow ppt = null;

        try {
            ZipSecureFile.setMinInflateRatio(-1.0d);
            is = new FileInputStream(pptFile);
            ppt = new HSLFSlideShow(is);
            int idx = 1;

            Dimension pageSize = ppt.getPageSize();
            double image_rate = 1.0;
            int imageWidth = (int) Math.floor(image_rate * pageSize.getWidth());
            int imageHeight = (int) Math.floor(image_rate * pageSize.getHeight());

            for (HSLFSlide slide : ppt.getSlides()) {
                BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = img.createGraphics();
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                // clear the drawing area
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, imageWidth, imageHeight));
                graphics.scale(image_rate, image_rate);

                //防止中文乱码
                for (HSLFShape shape : slide.getShapes()) {
                    if (shape instanceof HSLFTextShape) {
                        HSLFTextShape hslfTextShape = (HSLFTextShape) shape;
                        for (HSLFTextParagraph hslfTextParagraph : hslfTextShape) {
                            for (HSLFTextRun hslfTextRun : hslfTextParagraph) {
                                hslfTextRun.setFontFamily("宋体");
                            }
                        }
                    }
                }

                FileOutputStream out = null;
                try {
                    slide.draw(graphics);
                    File pngFile = new File(pptFile.getPath().replace(".ppt", String.format("-%04d.png", idx++)));
                    out = new FileOutputStream(pngFile);
                    ImageIO.write(img, "png", out);
                    pngFileList.add(pngFile);
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    try {
                        if (out != null) {
                            out.flush();
                            out.close();
                        }

                        if (graphics != null) {
                            graphics.dispose();
                        }

                        if (img != null) {
                            img.flush();
                        }
                    } catch (IOException e) {
                        //LOGGER.error("ppt2Png close exception", e);
                        System.out.println(e);
                    }
                }
            }
        } catch (Exception e) {
            //LOGGER.error("ppt2Png exception", e);
            System.out.println(e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }

                if (ppt != null) {
                    ppt.close();
                }
            } catch (Exception e) {
                //LOGGER.error("ppt2Png exception", e);
                System.out.println(e);
            }
        }
        long endTime = System.currentTimeMillis();
       // LOGGER.info("ppt2Png的时间：{}", endTime - startTime);
        //ystem.out.println(e);
        return pngFileList;
    }

    public static List<File> pptx2Png(File pptxFile) {
        List<File> pngFileList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        FileInputStream is = null;
        // 将ppt文件转换成每一帧的图片
        XMLSlideShow pptx = null;

        try {
            ZipSecureFile.setMinInflateRatio(-1.0d);
            is = new FileInputStream(pptxFile);
            pptx = new XMLSlideShow(is);
            int idx = 1;

            Dimension pageSize = pptx.getPageSize();
            double image_rate = 1.0;
            int imageWidth = (int) Math.floor(image_rate * pageSize.getWidth());
            int imageHeight = (int) Math.floor(image_rate * pageSize.getHeight());

            for (XSLFSlide xslfSlide : pptx.getSlides()) {
                BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = img.createGraphics();
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                // clear the drawing area
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, imageWidth, imageHeight));
                graphics.scale(image_rate, image_rate);

                //防止中文乱码
                for (XSLFShape shape : xslfSlide.getShapes()) {
                    if (shape instanceof XSLFTextShape) {
                        XSLFTextShape xslfTextShape = (XSLFTextShape) shape;
                        for (XSLFTextParagraph xslfTextParagraph : xslfTextShape) {
                            for (XSLFTextRun xslfTextRun : xslfTextParagraph) {
                                xslfTextRun.setFontFamily("宋体");
                            }
                        }
                    }
                }

                FileOutputStream out = null;
                try {
                    xslfSlide.draw(graphics);
                    File pngFile = new File(pptxFile.getPath().replace(".pptx", String.format("-%04d.png", idx++)));
                    out = new FileOutputStream(pngFile);
                    ImageIO.write(img, "png", out);
                    pngFileList.add(pngFile);
                } catch (Exception e) {
                    //LOGGER.error("pptx2Png exception", e);
                    System.out.println(e);
                } finally {
                    try {
                        if (out != null) {
                            out.flush();
                            out.close();
                        }

                        if (graphics != null) {
                            graphics.dispose();
                        }

                        if (img != null) {
                            img.flush();
                        }
                    } catch (IOException e) {
                        //LOGGER.error("pptx2Png close exception", e);
                        System.out.println(e);
                    }
                }
            }
        } catch (Exception e) {
            //LOGGER.error("pptx2Png exception", e);
            System.out.println(e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }

                if (pptx != null) {
                    pptx.close();
                }
            } catch (Exception e) {
                //LOGGER.error("pptx2Png exception", e);
                System.out.println(e);
            }
        }
        long endTime = System.currentTimeMillis();
        //LOGGER.info("pptx2Png耗时：{}", endTime - startTime);
        return pngFileList;
    }

    public static File png2Pdf(List<File> pngFiles, String pdfFilePath) {
        Document document = new Document();
        File inPdfFile = null;
        long startTime = System.currentTimeMillis();
        try {
            //String inFilePath = pdfFilePath.replace(".pdf", ".in.pdf");
            inPdfFile = new File(pdfFilePath);
            PdfWriter.getInstance(document, new FileOutputStream(inPdfFile));
            document.open();

            pngFiles.forEach(pngFile -> {
                try {
                    Image png = Image.getInstance(pngFile.getCanonicalPath());
                    png.scalePercent(50);
                    document.add(png);
                } catch (Exception e) {
                    //LOGGER.error("png2Pdf exception", e);
                    System.out.println(e);
                }
            });
            document.close();

            // 添加水印
           // boolean ret = PDFWatermarkUtil.addWatermark(inFilePath, pdfFilePath, watermarkPath);
           /* if (ret) {
                File pdfFile = new File(pdfFilePath);
                return pdfFile;
            }*/
        } catch (Exception e) {
            //LOGGER.error(String.format("png2Pdf %s exception", pdfFilePath), e);
            System.out.println(e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            /*if (inPdfFile != null) {
                inPdfFile.delete();
            }*/
            long endTime = System.currentTimeMillis();
            //LOGGER.info("png2Pdf耗时：{}", endTime - startTime);
            System.out.println(endTime - startTime);
        }

        return null;
    }
}

