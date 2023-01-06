package com.lab.doc.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.util.PoitlIOUtils;
import com.lab.doc.bean.SignatureConfig;
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

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class SignatureController {

    @Resource
    private SignatureConfig signatureConfig;

    private static final Logger logger = LoggerFactory.getLogger(SignatureController.class);

    private static final String SAVE_PATH = "D:" + File.separator + "file" + File.separator + "doc" + File.separator + "";
    private static final String SAVE_PATH_EXCEL = "D:" + File.separator + "file" + File.separator + "excel" + File.separator + "";

    @RequestMapping(value = "/word/signature", produces = MediaType.IMAGE_JPEG_VALUE)
    public void wordSignature(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HashMap<String, Object> hashMap = new HashMap<String, Object>() {{
            put("sign1", Pictures.ofStream(new ClassPathResource(String.format("images/签名图片/%s", signatureConfig.getSign1())).getStream(), PictureType.PNG)
                    .size(50, 25).create());
            put("sign2", Pictures.ofStream(new ClassPathResource(String.format("images/签名图片/%s", signatureConfig.getSign2())).getStream(), PictureType.PNG)
                    .size(50, 25).create());
            put("sign3", Pictures.ofStream(new ClassPathResource(String.format("images/签名图片/%s", signatureConfig.getSign3())).getStream(), PictureType.PNG)
                    .size(50, 25).create());
            put("sign4", Pictures.ofStream(new ClassPathResource(String.format("images/签名图片/%s", signatureConfig.getSign4())).getStream(), PictureType.PNG)
                    .size(50, 25).create());
            put("sign5", Pictures.ofStream(new ClassPathResource(String.format("images/签名图片/%s", signatureConfig.getSign5())).getStream(), PictureType.PNG)
                    .size(50, 25).create());
        }};
        String fileName = String.format("设计任务书模板(软件)-%s.docx", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        String agent = request.getHeader("User-Agent").toLowerCase();
        String browserName = getBrowserName(agent);

        String downFileName = null;

        boolean isMSIE = browserName.indexOf("ie") != -1;
        if (isMSIE) {
            logger.info("---- ie内核 ----");
            downFileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        } else {
            logger.info("------ chrome ----");
            downFileName = new String(fileName.getBytes("UTF8"), "ISO8859-1");
        }
        File file = new File(SAVE_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=\"" + downFileName + "\"");
        try (XWPFTemplate template = XWPFTemplate.compile(new ClassPathResource("tml/设计任务书模板(软件).docx").getStream()).render(hashMap);
             FileOutputStream outputStream = new FileOutputStream(SAVE_PATH + fileName); OutputStream out = response.getOutputStream();
             BufferedOutputStream bos = new BufferedOutputStream(out)) {
            template.write(outputStream);
            template.write(bos);
            outputStream.flush();
            bos.flush();
            out.flush();
            PoitlIOUtils.closeQuietlyMulti(template, outputStream, bos, out);
        } catch (Exception e) {
            logger.error("设计任务书模板签名失败", e);
        }
    }


    public String getBrowserName(String agent) {
        if (agent.indexOf("msie 7") > 0) {
            return "ie7";
        } else if (agent.indexOf("msie 8") > 0) {
            return "ie8";
        } else if (agent.indexOf("msie 9") > 0) {
            return "ie9";
        } else if (agent.indexOf("msie 10") > 0) {
            return "ie10";
        } else if (agent.indexOf("msie") > 0) {
            return "ie";
        } else if (agent.indexOf("opera") > 0) {
            return "opera";
        } else if (agent.indexOf("opera") > 0) {
            return "opera";
        } else if (agent.indexOf("firefox") > 0) {
            return "firefox";
        } else if (agent.indexOf("webkit") > 0) {
            return "webkit";
        } else if (agent.indexOf("gecko") > 0 && agent.indexOf("rv:11") > 0) {
            return "ie11";
        } else {
            return "Others";
        }
    }


    /**
     * 最简单的填充
     *
     * @since 2.1.1
     */
    @RequestMapping(value = "/excel/signature", produces = MediaType.IMAGE_JPEG_VALUE)
    public void excelFill(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        String templateFileName = new ClassPathResource("tml/2022(ZTY)-1279.xls").getAbsolutePath();

        File file = new File(SAVE_PATH_EXCEL);
        if (!file.exists()) {
            file.mkdir();
        }
        String fileName = String.format("2022(ZTY)-1279-%s.xls", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        // 根据Map填充
        String fullFileName = SAVE_PATH_EXCEL + String.format("2022(ZTY)-1279-%s.xls", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        Map<String, Object> map = MapUtils.newHashMap();
        map.put("name", "张三");
        map.put("number", 5.2);
        EasyExcel.write(fullFileName).withTemplate(templateFileName).sheet().doFill(map);

        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String downFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + downFileName);
        EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).sheet().doFill(map);
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
        System.out.println(new ClassPathResource("tml/2022(ZTY)-1279.xls").getAbsolutePath());
        try (ExcelWriter writer = ExcelUtil.getWriter(new ClassPathResource("tml/2022(ZTY)-1279.xls").getAbsolutePath());) {
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
            logger.error("2022(ZTY)-1279出错了", e);
        }


        File f = new File(tmpFile.toURI());
        if (f.delete()) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }

    @PostMapping(value = "/signature3", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public BufferedImage pushHousingPhotoMethod3(@RequestBody MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String ext = StringUtils.substring(fileName, fileName.lastIndexOf('.'), fileName.length());
        File tmpFile = File.createTempFile(this.make32BitUUID(), ext);
        file.transferTo(tmpFile); //转储临时文件
        final BufferedImage bufferedImage = PictureUtil.getThumbBufferedImage(tmpFile);


        try (ExcelWriter writer = ExcelUtil.getWriter(new ClassPathResource("tml/2022(ZTY)-1279.xls").getAbsolutePath())) {
            writer.setDefaultRowHeight(70);
            writer.setColumnWidth(-1, 30);
            //测试写入10个图片
            for (int i = 0; i < 10; i++) {
                //读取图片
                byte[] pictureData = FileUtil.readBytes("images/签名图片/丁振.png");
                //写入图片
                writePic(writer, 0, i, pictureData, HSSFWorkbook.PICTURE_TYPE_PNG);
            }
            byte[] pictureData = FileUtil.readBytes("images/签名图片/丁振.png");
            writePic(writer, 3, 24, pictureData, HSSFWorkbook.PICTURE_TYPE_PNG);
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * postman body binary 文件上传
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/uploadPic")
    @ResponseBody
    public String uploadPic(HttpServletRequest request) {
        File file = new File("E:\\test.jpg");
        if (file != null) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedInputStream bi = new BufferedInputStream(request.getInputStream());
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))
        ) {
            // 指定要写入文件的缓冲输出字节流
            byte[] bb = new byte[1024];// 用来存储每次读取到的字节数组
            int n;// 每次读取到的字节数组的长度
            while ((n = bi.read(bb)) != -1) {
                out.write(bb, 0, n);// 写入到输出流
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "async";
    }
}
