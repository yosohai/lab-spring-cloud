package com.lab.doc.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.StrUtil;
import com.lab.doc.utils.ImageUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.UUID;

@Controller
public class ThumbController {

    private static final Logger logger = LoggerFactory.getLogger(ThumbController.class);

    private static final String SAVE_PATH_IMAGES = "D:" + File.separator + "file" + File.separator + "images" + File.separator + "";

    @RequestMapping(value = "/thumbnails")
    public void thumbnails(@RequestParam("thumb") String thumb, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (StrUtil.isBlank(thumb)) {
            throw new Exception("上传失败，上传图片数据为空");
        }

        String[] strArr = thumb.split("base64,");
        if (strArr == null || strArr.length != 2) {
            throw new Exception("上传失败，数据不合法");
        }

        logger.debug("对数据进行解析，获取文件名和流数据");
        String suffix = "", dataPrefix = strArr[0];
//        if ("data:image/jpeg;".equalsIgnoreCase(dataPrefix)) { // data:image/jpeg;base64,base64编码的jpeg图片数据
//            suffix = ".jpg";
//        } else if ("data:image/x-icon;".equalsIgnoreCase(dataPrefix)) { // data:image/x-icon;base64,base64编码的icon图片数据
//            suffix = ".ico";
//        } else if ("data:image/gif;".equalsIgnoreCase(dataPrefix)) { // data:image/gif;base64,base64编码的gif图片数据
//            suffix = ".gif";
//        } else if ("data:image/png;".equalsIgnoreCase(dataPrefix)) { // data:image/png;base64,base64编码的png图片数据
//            suffix = ".png";
//        } else {
//            throw new Exception("上传图片格式不合法");
//        }

        File file = new File(SAVE_PATH_IMAGES);
        if (!file.exists()) {
            file.setWritable(true);
            file.mkdirs();
        }


        //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
        byte[] bytes = Base64Utils.decodeFromString(strArr[1]);
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            Thumbnails.Builder<BufferedImage> builder = Thumbnails.of(bufferedImage)
                    .size(150, 70)//尺寸
                    .outputQuality(1.0f);//缩略图质量
            suffix = FileTypeUtil.getType(new ByteArrayInputStream(bytes));
            String imgName = UUID.randomUUID().toString().replace("-", "");
            String tempFileName = imgName + "." + suffix;
            logger.debug("生成文件名为：" + tempFileName);
            BufferedImage image = builder.asBufferedImage();
            FileUtils.writeByteArrayToFile(new File(SAVE_PATH_IMAGES + tempFileName), ImageUtils.bufferedImageToBytes(image));
            /*--------------------上面保存到磁盘，下面返回缩略图流数据-----------------------------------*/
//            String smallerBase64 = new BASE64Encoder().encodeBuffer(ImageUtils.bufferedImageToBytes(image));//转换成base64串
//            smallerBase64 = smallerBase64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n

            String smallerBase64 = Base64Utils.encodeToString(ImageUtils.bufferedImageToBytes(image));
            response.getWriter().write(smallerBase64);
        } catch (Exception e) {
            logger.error("生成签名缩略图失败", e);
        }
    }

}