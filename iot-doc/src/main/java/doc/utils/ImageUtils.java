package doc.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/*************************************
 *Class Name: ImageUtils
 *Description: <图片处理工具类>
 *@author: lzqing
 *@since 1.0.0
 *************************************/
public class ImageUtils {

    /**
     * byte[] 转 BufferedImage
     *
     * @param bytes
     * @return
     * @throws java.io.IOException
     */
    public static BufferedImage bytesToBufferedImage(byte[] bytes) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);  // 将bytes作为输入流
        BufferedImage image = ImageIO.read(in);     //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
        return image;
    }

    /**
     * BufferedImage 转 byte[]
     *
     * @param image
     * @return
     * @throws java.io.IOException
     */
    public static byte[] bufferedImageToBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return outputStream.toByteArray();
    }

    /**
     * 读取图片为灰度图
     *
     * @param imagePath 图片绝对路径
     * @return
     * @throws java.io.IOException
     */
    public static BufferedImage readAsGrayImage(String imagePath) throws IOException {
        BufferedImage src = ImageIO.read(new File(imagePath));  //BufferedImage 类的图片资源
        BufferedImage grayImage = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < src.getWidth(); i++) {
            for (int j = 0; j < src.getHeight(); j++) {
                int rgb = grayImage.getRGB(i, j);
                grayImage.setRGB(i, j, rgb);
            }
        }
        return grayImage;
    }

    /**
     * 读取图片后截取
     *
     * @param imagePath 图片绝对路径
     * @param width     要截取的图片区域宽度
     * @param height    要截取的图片区域高度
     * @return BufferedImage 截取后的图片
     * @throws java.io.IOException
     */
    public static BufferedImage cropImage(String imagePath, int width, int height) throws IOException {
        BufferedImage src = ImageIO.read(new File(imagePath));  //BufferedImage 类的图片资源
        BufferedImage crop = src.getSubimage(src.getWidth() - width, src.getHeight() - height, width, height);
        return crop;
    }

    /**
     * 图片截取并转灰度图
     *
     * @param src    原始图片
     * @param width  要截取的图片区域宽度
     * @param height 要截取的图片区域高度
     * @return BufferedImage 截取后的图片
     * @throws java.io.IOException
     */
    public static BufferedImage cropImageToGray(BufferedImage src, int width, int height) throws IOException {
        // 图片截取
        BufferedImage crop = src.getSubimage(src.getWidth() - width, src.getHeight() - height, width, height);
        File cropOutputFile = new File("E:\\mat\\mvt\\java-ocr-demo\\images\\crop.jpg");
        ImageIO.write(crop, "jpg", cropOutputFile);

        // 图片转灰度图
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = crop.getRGB(i, j);
                grayImage.setRGB(i, j, rgb);
            }
        }
        // 保存图片到本地
        File outputfile = new File("E:\\mat\\mvt\\java-ocr-demo\\images\\crop_gray.jpg");
        ImageIO.write(grayImage, "jpg", outputfile);
        return crop;
    }
}
