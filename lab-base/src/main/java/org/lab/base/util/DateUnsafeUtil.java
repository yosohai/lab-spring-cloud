package org.lab.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述：日期工具
 */
public class DateUnsafeUtil {

    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(Date date) throws ParseException {
        return sdf.format(date);
    }

    public static Date parse(String strDate) throws ParseException {
        return sdf.parse(strDate);
    }

    public static void main(String[] args) throws InterruptedException, ParseException {

        System.out.println(sdf.format(new Date()));

    }

}
