package org.lab.base.test;

import org.junit.jupiter.api.Test;
import org.lab.base.util.TextUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextUtilTest {

    @Test
    public void getLink() throws Exception {
        String text = "链接：https://pan.baidu.com/s/14Ohd4jLuMWLRtqIt6eUNKg\n" +
                "提取码：5dlw\n" +
                "复制这段内容后打开百度网盘手机App，操作更方便哦";
        String str = TextUtil.getLink(text);
        assertEquals("https://pan.baidu.com/s/14Ohd4jLuMWLRtqIt6eUNKg", str);
    }


    @Test
    public void getLink1() throws Exception {
        String text = "可以的";
        String str = TextUtil.getLink(text);
        assertEquals("", str);
    }

    @Test
    public void getCode() throws Exception {
        String text = "链接：https://pan.baidu.com/s/14Ohd4jLuMWLRtqIt6eUNKg\n" +
                "提取码：5dlw\n" +
                "复制这段内容后打开百度网盘手机App，操作更方便哦";
        String str = TextUtil.getCode(text);
        assertEquals("5dlw", str);
    }

}
