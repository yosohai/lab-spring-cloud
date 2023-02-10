package doc.utils;

import java.io.File;

/**
 * 文件工具类
 */
public class IotFileUtil {

    /**
     * 确保保存路径存在 不存在则新建
     *
     * @param savePath 保存路径
     */
    public static void ensureFileExist(String savePath) {
        File file = new File(savePath);
        if (!file.exists()) {
            file.setWritable(true);
            file.mkdirs();
        }
    }
}
