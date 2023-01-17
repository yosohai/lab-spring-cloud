package com.lab.doc.controller;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.util.PoitlIOUtils;
import com.lab.doc.config.Excel2SignatureConfig;
import com.lab.doc.config.ExcelSignatureConfig;
import com.lab.doc.config.WordSignatureConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SignatureController {

    @Resource
    private ExcelSignatureConfig excelSignatureConfig;

    @Resource
    private Excel2SignatureConfig excel2SignatureConfig;

    @Resource
    private WordSignatureConfig wordSignatureConfig;

    @Resource
    private Environment environment;

    private static final Logger logger = LoggerFactory.getLogger(SignatureController.class);

    private static final String SAVE_PATH_DOC = "D:" + File.separator + "file" + File.separator + "doc" + File.separator + "";
    private static final String SAVE_PATH_EXCEL = "D:" + File.separator + "file" + File.separator + "excel" + File.separator + "";

    @RequestMapping(value = "/word/signature", produces = MediaType.IMAGE_JPEG_VALUE)
    public void wordSignature(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final List<String> list = new ArrayList<>();
        ReflectionUtils.doWithFields(WordSignatureConfig.class, field -> list.add(field.getName()));

        HashMap<String, Object> hashMap = new HashMap<String, Object>() {{
            for (String key : list) {
                Field field = ReflectionUtils.findField(WordSignatureConfig.class, key);
                ReflectionUtils.makeAccessible(field);
                String value = (String) ReflectionUtils.getField(field, wordSignatureConfig);
                put(key, Pictures.ofStream(new ClassPathResource(String.format("images/签名图片/%s", value)).getStream(), PictureType.PNG)
                        .size(50, 25).create());
            }
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
        File file = new File(SAVE_PATH_DOC);
        if (!file.exists()) {
            file.setWritable(true);
            file.mkdirs();
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=\"" + downFileName + "\"");
        try (XWPFTemplate template = XWPFTemplate.compile(new ClassPathResource("tml/设计任务书模板(软件).docx").getStream()).render(hashMap);
             FileOutputStream outputStream = new FileOutputStream(SAVE_PATH_DOC + fileName); OutputStream out = response.getOutputStream();
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
     * excel填充
     */
    @RequestMapping(value = "/excel/signature", produces = MediaType.IMAGE_JPEG_VALUE)
    public void excelFill(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        String templateFileName = new ClassPathResource("tml/2022(ZTY)-1279.xls").getAbsolutePath();

        File file = new File(SAVE_PATH_EXCEL);
        if (!file.exists()) {
            file.setWritable(true);
            file.mkdirs();
        }
        String fileName = String.format("2022(ZTY)-1279-%s.xls", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        // 根据Map填充
        String fullFileName = SAVE_PATH_EXCEL + String.format("2022(ZTY)-1279-%s.xls", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        Map<String, Object> map = MapUtils.newHashMap();
//        map.put("name", "张三");
//        map.put("number", 5.2);

        final List<String> list = new ArrayList<>();
        ReflectionUtils.doWithFields(ExcelSignatureConfig.class, field -> list.add(field.getName()));

        // 填充图片
        ByteArrayOutputStream byteArrayOut = null;
        for (String key : list) {
            byteArrayOut = new ByteArrayOutputStream();
            Field field = ReflectionUtils.findField(ExcelSignatureConfig.class, key);
            ReflectionUtils.makeAccessible(field);
            String value = (String) ReflectionUtils.getField(field, excelSignatureConfig);
            //通过反射获取对象
            BufferedImage bufferImg = ImageIO.read(new ClassPathResource(String.format("images/签名图片/%s", value)).getStream());
            // 图片后缀格式
            String picName = value;
            String picSuffix = picName.substring(picName.lastIndexOf(".") + 1);
            ImageIO.write(bufferImg, picSuffix, byteArrayOut);
            bufferImg.flush();
            // 注意：这里需要put回原来的key里
            map.put(key, byteArrayOut.toByteArray());
        }


        ExcelWriter excelWriter = EasyExcel.write(fullFileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
        excelWriter.fill(map, writeSheet);
        if (fileName.toUpperCase().endsWith(".XLS")) {
            response.setContentType(ExcelUtil.XLS_CONTENT_TYPE);
        } else if (fileName.toUpperCase().endsWith(".XLSX")) {
            response.setContentType(ExcelUtil.XLSX_CONTENT_TYPE);
        }
        response.setCharacterEncoding("utf-8");
        // URLEncoder.encode可以防止中文乱码
        String downFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + downFileName);
        EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).sheet().doFill(map);
    }

    /**
     * excel填充
     */
    @RequestMapping(value = "/excel2/signature", produces = MediaType.IMAGE_JPEG_VALUE)
    public void excel2Fill(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        String templateFileName = new ClassPathResource("tml/产品物料清单配置表模板.xls").getAbsolutePath();

        File file = new File(SAVE_PATH_EXCEL);
        if (!file.exists()) {
            file.setWritable(true);
            file.mkdirs();
        }
        String fileName = String.format("产品物料清单配置表模板-%s.xls", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        // 根据Map填充
        String fullFileName = SAVE_PATH_EXCEL + String.format("产品物料清单配置表模板-%s.xls", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        Map<String, Object> map = MapUtils.newHashMap();
//        map.put("name", "张三");
//        map.put("number", 5.2);

        // 填充图片
        ByteArrayOutputStream byteArrayOut = null;

        final List<String> list = new ArrayList<>();
        ReflectionUtils.doWithFields(Excel2SignatureConfig.class, field -> list.add(field.getName()));
        for (String key : list) {
            byteArrayOut = new ByteArrayOutputStream();
            Field field = ReflectionUtils.findField(Excel2SignatureConfig.class, key);
            ReflectionUtils.makeAccessible(field);
            String value = (String) ReflectionUtils.getField(field, excel2SignatureConfig);
            //通过反射获取对象
            BufferedImage bufferImg = ImageIO.read(new ClassPathResource(String.format("images/签名图片/%s", value)).getStream());
            // 图片后缀格式
            String picName = value;
            String picSuffix = picName.substring(picName.lastIndexOf(".") + 1);
            ImageIO.write(bufferImg, picSuffix, byteArrayOut);
            bufferImg.flush();
            // 注意：这里需要put回原来的key里
            map.put(key, byteArrayOut.toByteArray());
        }

        com.alibaba.excel.ExcelWriter excelWriter = EasyExcel.write(fullFileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
        excelWriter.fill(map, writeSheet);
        if (fileName.toUpperCase().endsWith(".XLS")) {
            response.setContentType(ExcelUtil.XLS_CONTENT_TYPE);
        } else if (fileName.toUpperCase().endsWith(".XLSX")) {
            response.setContentType(ExcelUtil.XLSX_CONTENT_TYPE);
        }
        response.setCharacterEncoding("utf-8");
        // URLEncoder.encode可以防止中文乱码
        String downFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + downFileName);
        EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).sheet().doFill(map);
    }

}
