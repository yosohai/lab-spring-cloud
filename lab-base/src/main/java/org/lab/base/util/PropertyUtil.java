package org.lab.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: lzqing
 * @Description: 利用Java的类加载机制, 通过该工具类获取属性值, 先要加载这个工具类的静态代码块
 * @Date: Created in 2021/6/6
 */
public class PropertyUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private static final String targetProsFileFormat = "application-%s.properties";
    private static Properties props;

    static {
        loadProps();
    }

    synchronized static private void loadProps() {
        logger.info("start to load properties.......");
        props = new Properties();
        InputStream in = null;
        try {
            in = PropertyUtil.class.getClassLoader().
                    getResourceAsStream("application.properties");
            props.load(in);
            String active = props.getProperty("spring.profiles.active");
            String targetProsFile = String.format(targetProsFileFormat, active);
            in = PropertyUtil.class.getClassLoader().
                    getResourceAsStream(targetProsFile);
            props.load(in);

        } catch (FileNotFoundException e) {
            logger.error("properties not found!");
        } catch (IOException e) {
            logger.error("IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("properties close Exception!");
            }
        }
        logger.info("load properties over...........");
    }

    public static String getProperty(String key) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }
}
