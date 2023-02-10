package doc.utils;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.deepoove.poi.data.PictureRenderData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 模板参数填充工具类
 */
public class ParamUtil {

    /**
     * 根据参数构造word填充数据
     *
     * @param dateKey 日期标志对应模板值
     * @param dateValue 日期值
     * @param map      签名映射值
     * @return
     */
    public static HashMap<String, Object> getWordFiller(String dateKey, String dateValue, Map<String, String> map) throws IOException {
        HashMap<String, Object> hashMap = new HashMap<String, Object>() {{
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (StrUtil.isBlank(key) || StrUtil.isBlank(value)) {
                    continue;
                }
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                BufferedImage bufferImg = ImageIO.read(new ClassPathResource(String.format("images/签名图片/%s", value)).getStream());
                // 图片后缀格式
                String picName = value;
                String picSuffix = picName.substring(picName.lastIndexOf("."));
                ImageIO.write(bufferImg, picSuffix, byteArrayOut);
                bufferImg.flush();
                PictureRenderData picData = new PictureRenderData(50, 25, picSuffix, ImageIO.read(new ClassPathResource(String.format("images/签名图片/%s", value)).getStream()));
                this.put(key, picData);
            }
        }};
        hashMap.put(dateKey, dateValue);
        return hashMap;
    }

    /**
     * 根据参数构造Excel填充数据
     *
     * @param dateKey 日期标志对应模板值
     * @param dateValue 日期值
     * @param map      签名映射值
     * @return
     */
    public static HashMap<String, Object> getExcelFiller(String dateKey, String dateValue, Map<String, String> map) throws IOException {
        HashMap<String, Object> fillerMap = new HashMap<String, Object>() {{
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (StrUtil.isBlank(key) || StrUtil.isBlank(value)) {
                    continue;
                }

                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                BufferedImage bufferImg = ImageIO.read(new ClassPathResource(String.format("images/签名图片/%s", value)).getStream());
                // 图片后缀格式
                String picName = value;
                String picSuffix = picName.substring(picName.lastIndexOf(".") + 1);
                ImageIO.write(bufferImg, picSuffix, byteArrayOut);
                bufferImg.flush();
                // 注意：这里需要put回原来的key里
                this.put(key, byteArrayOut.toByteArray());
            }
        }};
        fillerMap.put(dateKey, dateValue);
        return fillerMap;
    }

}
