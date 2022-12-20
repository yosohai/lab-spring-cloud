package com.lab.doc.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.lab.doc.utils.PictureUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.UUID;

@Controller
public class SignatureController {


    private static final Logger logger = LoggerFactory.getLogger(SignatureController.class);


    @PostMapping(value = "/word/signature", produces = MediaType.IMAGE_JPEG_VALUE)
    public BufferedImage wordSignature(MultipartFile file) throws IOException {


        HashMap<String, Object> hashMap = new HashMap<String, Object>() {{
            put("sign1", Pictures.ofStream(new ClassPathResource("images/签名图片/丁振.png").getStream(), PictureType.PNG)
                    .size(50, 25).create());
            put("sign2", Pictures.ofStream(new ClassPathResource("images/签名图片/叶怀花.png").getStream(), PictureType.PNG)
                    .size(50, 25).create());
            put("sign3", Pictures.ofStream(new ClassPathResource("images/签名图片/吴军.png").getStream(), PictureType.PNG)
                    .size(50, 25).create());
            put("sign4", Pictures.ofStream(new ClassPathResource("images/签名图片/李文辉.png").getStream(), PictureType.PNG)
                    .size(50, 25).create());
        }};

        try (XWPFTemplate template = XWPFTemplate.compile(new ClassPathResource("tml/设计任务书模板(软件).docx").getStream()).render(hashMap);
             FileOutputStream outputStream = new FileOutputStream("D:\\file\\文档1.docx")) {
            template.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fileName = file.getOriginalFilename();
        String ext = StringUtils.substring(fileName, fileName.lastIndexOf('.'), fileName.length());
        File tmpFile = File.createTempFile(this.make32BitUUID(), ext);
        file.transferTo(tmpFile); //转储临时文件
        BufferedImage image = ImageIO.read(new FileInputStream(tmpFile));
        File f = new File(tmpFile.toURI());
        if (f.delete()) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
        return image;
    }

    @PostMapping(value = "/signature", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public BufferedImage pushHousingPhotoMethod(@RequestBody MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String ext = StringUtils.substring(fileName, fileName.lastIndexOf('.'), fileName.length());
        File tmpFile = File.createTempFile(this.make32BitUUID(), ext);
        file.transferTo(tmpFile); //转储临时文件
        BufferedImage image = ImageIO.read(new FileInputStream(tmpFile));
        File f = new File(tmpFile.toURI());
        if (f.delete()) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
        return image;
    }

    @PostMapping(value = "/signature2")
    @ResponseBody
    public void pushHousingPhotoMethod2(@RequestBody MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String ext = StringUtils.substring(fileName, fileName.lastIndexOf('.'), fileName.length());
        File tmpFile = File.createTempFile(this.make32BitUUID(), ext);
        file.transferTo(tmpFile); //转储临时文件
        File toFile = File.createTempFile(this.make32BitUUID(), ext);
        PictureUtil.photoSmaller(tmpFile, toFile);

        try (ExcelWriter writer = ExcelUtil.getWriter("classpath:2022(ZTY)-1279.xls");) {
            writer.setDefaultRowHeight(70);
            writer.setColumnWidth(-1, 30);
            //测试写入10个图片
            for (int i = 0; i < 10; i++) {
                //读取图片
                byte[] pictureData = FileUtil.readBytes(toFile);
                //写入图片
                writePic(writer, 0, i, pictureData, HSSFWorkbook.PICTURE_TYPE_JPEG);
            }
        } catch (Exception e) {

        }


        File f = new File(tmpFile.toURI());
        if (f.delete()) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
        return;
    }

    @PostMapping(value = "/signature3", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public BufferedImage pushHousingPhotoMethod3(@RequestBody MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String ext = StringUtils.substring(fileName, fileName.lastIndexOf('.'), fileName.length());
        File tmpFile = File.createTempFile(this.make32BitUUID(), ext);
        file.transferTo(tmpFile); //转储临时文件
        final BufferedImage bufferedImage = PictureUtil.getSmallerBufferedImage(tmpFile);


        try (ExcelWriter writer = ExcelUtil.getWriter("classpath:2022(ZTY)-1279.xls");) {
            writer.setDefaultRowHeight(70);
            writer.setColumnWidth(-1, 30);
            //测试写入10个图片
            for (int i = 0; i < 10; i++) {
                //读取图片
                byte[] pictureData = FileUtil.readBytes("img/1.jpg");
                //写入图片
                writePic(writer, 0, i, pictureData, HSSFWorkbook.PICTURE_TYPE_JPEG);
            }
        } catch (Exception e) {

        }


        File f = new File(tmpFile.toURI());
        if (f.delete()) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
        return bufferedImage;
    }

    /**
     * 生成一个32位无横杠的UUID
     */
    public synchronized static String make32BitUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @RequestMapping(value = "/get", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public BufferedImage getImage() throws IOException {
        return ImageIO.read(new FileInputStream(new File("D:/test.jpg")));
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

    /**
     * @param writer
     * @param x           单元格x轴坐标
     * @param y           单元格y轴坐标
     * @param pictureData 图片二进制数据
     * @param picType     图片格式
     */
    private void writePic(ExcelWriter writer, int x, int y, File pictureData, int picType) throws IOException {
        Sheet sheet = writer.getSheet();
        Drawing drawingPatriarch = sheet.createDrawingPatriarch();

        //设置图片单元格位置
        ClientAnchor anchor = drawingPatriarch.createAnchor(0, 0, 0, 0, x, y, x + 1, y + 1);
        //随单元格改变位置和大小
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);

        //添加图片
        int pictureIndex = sheet.getWorkbook().addPicture(Files.readAllBytes(pictureData.toPath()), picType);
        drawingPatriarch.createPicture(anchor, pictureIndex);
    }

    public static byte[] getBytesByFile(String filePath) {
        try {
            File file = new File(filePath);
            //获取输入流
            FileInputStream fis = new FileInputStream(file);

            //新的 byte 数组输出流，缓冲区容量1024byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            //缓存
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            //改变为byte[]
            byte[] data = bos.toByteArray();
            //
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "getData")
    @ResponseBody
    public String getData(HttpServletRequest request) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));) {
            //获取请求参数
            StringBuffer sb = new StringBuffer("");
            String temp = null;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            String inputParamsJsonString = sb.toString();
            logger.info("inputParamsJsonString=" + inputParamsJsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "async";
    }
}
