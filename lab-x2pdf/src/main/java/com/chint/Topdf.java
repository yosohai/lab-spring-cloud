package com.chint;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.net.MalformedURLException;

/**
 * @author cai
 * @version 1.0
 * @date 2021/1/4 14:58
 */
public class Topdf {


    /**
     * 转pdf doc docx xls xlsx
     * @param path
     */
    public void docTopdf(String path) {

        File inputWord = new File("C:\\Users\\29934\\Documents\\Tencent Files\\2993481541\\FileRecv\\1111.docx");
        File outputFile = new File("C:\\Users\\29934\\Documents\\Tencent Files\\2993481541\\FileRecv\\1111.pdf");
        try  {
            InputStream docxInputStream = new FileInputStream(inputWord);
            OutputStream outputStream = new FileOutputStream(outputFile);
            IConverter converter = LocalConverter.builder().build();
            String fileTyle=path.substring(path.lastIndexOf("."),path.length());//获取文件类型
            if(".docx".equals(fileTyle)){
                converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
            }else if(".doc".equals(fileTyle)){
                converter.convert(docxInputStream).as(DocumentType.DOC).to(outputStream).as(DocumentType.PDF).execute();
            }else if(".xls".equals(fileTyle)){
                converter.convert(docxInputStream).as(DocumentType.XLS).to(outputStream).as(DocumentType.PDF).execute();
            }else if(".xlsx".equals(fileTyle)){
                converter.convert(docxInputStream).as(DocumentType.XLSX).to(outputStream).as(DocumentType.PDF).execute();
            }
            outputStream.close();
            System.out.println("pdf转换成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     *            生成pdf文件
     *            需要转换的图片路径的数组
     */
    public static void main(String[] args) {
        try {
            String imagesPath = "C:\\Users\\29934\\Documents\\Tencent Files\\2993481541\\FileRecv\\1111.jpg";
            File file = new File("C:\\Users\\29934\\Documents\\Tencent Files\\2993481541\\FileRecv\\1111.pdf");
            // 第一步：创建一个document对象。
            Document document = new Document();
            document.setMargins(0, 0, 0, 0);
            // 第二步：
            // 创建一个PdfWriter实例，
            PdfWriter.getInstance(document, new FileOutputStream(file));
            // 第三步：打开文档。
            document.open();
            // 第四步：在文档中增加图片。
            if (true) {
                Image img = Image.getInstance(imagesPath);
                img.setAlignment(Image.ALIGN_CENTER);
                // 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
                document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                document.newPage();
                document.add(img);
                //下面是对应一个文件夹的图片
//            File files = new File(imagesPath);
//            String[] images = files.list();
//            int len = images.length;
//
//            for (int i = 0; i < len; i++)
//            {
//                if (images[i].toLowerCase().endsWith(".bmp")
//                        || images[i].toLowerCase().endsWith(".jpg")
//                        || images[i].toLowerCase().endsWith(".jpeg")
//                        || images[i].toLowerCase().endsWith(".gif")
//                        || images[i].toLowerCase().endsWith(".png")) {
//                    String temp = imagesPath + "\\" + images[i];
//                    Image img = Image.getInstance(temp);
//                    img.setAlignment(Image.ALIGN_CENTER);
//                    // 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
//                    document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
//                    document.newPage();
//                    document.add(img);
//                }
//            }
                // 第五步：关闭文档。
                document.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}

