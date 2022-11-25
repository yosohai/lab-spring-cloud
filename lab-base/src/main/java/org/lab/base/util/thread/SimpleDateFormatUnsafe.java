package org.lab.base.util.thread;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SimpleDateFormatUnsafe {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
//   线程不安全                 System.out.println(Thread.currentThread().getName() + ":" + DateUnsafeUtil.parse("2018-01-02 09:45:59"));
                    System.out.println(Thread.currentThread().getName() + ":" + DateSafeUtil.parse("2018-01-02 09:45:59"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }, "MyTread" + i).start();
        }

        IntStream.range(0, 5).boxed().map(i -> new Thread(() -> System.out.println(Thread.currentThread().getName()))).forEach(Thread::start);
    }
}

class DateSafeUtil {

    private static final ThreadLocal<DateFormat> sdf = ThreadLocal.withInitial(() -> {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    });


    public static String formatDate(Date date) throws ParseException {
        return sdf.get().format(date);
    }

    public static Date parse(String strDate) throws ParseException {
        return sdf.get().parse(strDate);
    }

    public static void main(String[] args) throws InterruptedException, ParseException {

        System.out.println(sdf.get().format(new Date()));

    }
}