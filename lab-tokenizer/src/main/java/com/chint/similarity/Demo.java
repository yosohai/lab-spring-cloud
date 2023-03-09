package com.chint.similarity;


import com.chint.similarity.text.CosineSimilarity;
import com.google.common.base.Stopwatch;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Demo {

    public static final String content1 = "今天小小和爸爸一起去摘草莓，小小说今天的草莓特别的酸，而且特别的小，关键价格还贵";
    public static final String content11 = "我是中国人";

    public static final String content2 = "今天小小和妈妈一起去草原里采草莓，今天的草莓味道特别好，而且价格还挺实惠的";
    public static final String content22 = "中国人,我是";


    public static void main(String[] args) throws UnsupportedEncodingException {

        double score = CosineSimilarity.getSimilarity(content1, content2);
        System.out.println("相似度：" + score);

        score = CosineSimilarity.getSimilarity(content11, content22);
        System.out.println("相似度：" + score);

        List<String> list = new ArrayList<>();
        IntStream.range(0, 10000).boxed().forEach(e -> {
                    try {
                        list.add("今天小小和妈妈一起去草原里采草莓，今天的草莓味道特别好，而且价格还挺实惠的" + Demo.create());
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        unsupportedEncodingException.printStackTrace();
                    }
                }
        );

        List<String> list1 = new ArrayList<>();
        IntStream.range(0, 10000).boxed().forEach(e -> {
                    try {
                        list1.add("今天小小和爸爸一起去摘草莓，小小说今天的草莓特别的酸，而且特别的小，关键价格还贵" + Demo.create());
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        unsupportedEncodingException.printStackTrace();
                    }
                }
        );

        Stopwatch started = Stopwatch.createStarted();
        for (int i = 0, len = list.size(); i < 1; i++) {
            for (int j = 0, len1 = list1.size(); j < len1; j++) {
                System.out.println(CosineSimilarity.getSimilarity(list.get(i), list1.get(j)));
            }
        }
        started.stop();  ///  1000*1000 101435   1000*10000 10300309
        System.out.println(started.elapsed(TimeUnit.MILLISECONDS));

    }

    public static String create() throws UnsupportedEncodingException {
        String str = null;
        int hightPos, lowPos; // 定义高低位
        Random random = new Random();
        hightPos = (176 + Math.abs(random.nextInt(39)));//获取高位值
        lowPos = (161 + Math.abs(random.nextInt(93)));//获取低位值
        byte[] b = new byte[2];
        b[0] = (new Integer(hightPos).byteValue());
        b[1] = (new Integer(lowPos).byteValue());
        return new String(b, "GBk");//转成中文
    }
}
