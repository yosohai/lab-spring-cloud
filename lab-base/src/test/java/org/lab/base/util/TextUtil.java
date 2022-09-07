package org.lab.base.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    public static String getLink(String text) {
        Pattern pattern = Pattern.compile("链接：(.*)");
        Matcher m = pattern.matcher(text);
        String str = "";
        if (m.find()) {
            str = m.group(1);
        }
        return str;
    }

    public static String getCode(String text) {
        Pattern pattern = Pattern.compile("提取码：(.*)");
        Matcher m = pattern.matcher(text);
        String str = "";
        if (m.find()) {
            str = m.group(1);
        }
        return str;
    }

}
