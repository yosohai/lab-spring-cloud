package com.chint.similarity;


import cn.hutool.core.lang.Pair;
import com.chint.similarity.text.CosineSimilarity;
import com.chint.similarity.util.ListUtil;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.springframework.util.StopWatch;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Demo2 {

    public static final String content1 = "今天小小和爸爸一起去摘草莓，小小说今天的草莓特别的酸，而且特别的小，关键价格还贵";
    public static final String content11 = "我是中国人";

    public static final String content2 = "今天小小和妈妈一起去草原里采草莓，今天的草莓味道特别好，而且价格还挺实惠的";
    public static final String content22 = "中国人,我是";


    public static void main(String[] args) throws Exception {

        List<String> list = new ArrayList<>();
        IntStream.range(0, 2000).boxed().forEach(e -> {
                    list.add("今天小小和妈妈一起去草原里采草莓，今天的草莓味道特别好，而且价格还挺实惠的" + Demo2.create());
                }
        );

        Stopwatch started = Stopwatch.createStarted();

        StopWatch stopWatch1 = new StopWatch("段一");
        stopWatch1.start();
        List<Pair<String, String>> pairs = ListUtil.concat(list);

        int total = pairs.size();
        int processors = Runtime.getRuntime().availableProcessors() + 1;

        int subListSize = total % processors == 0 ? total / processors : total / processors + 1;

        List<List<Pair<String, String>>> subSets = Lists.partition(pairs, subListSize);
        stopWatch1.stop();
        System.out.println("stopWatch1" + stopWatch1.prettyPrint());
        ExecutorService executorService = Executors.newFixedThreadPool(processors);

        List<Double> result = new ArrayList<>();
        for (int i = 0, size = subSets.size(); i < size; i++) {
            final List<Pair<String, String>> subList = subSets.get(i);
            FutureTask<List<Double>> task = new FutureTask<List<Double>>(new Callable<List<Double>>() {
                @Override
                public List<Double> call() throws Exception {
                    int len = subList.size();
                    List<Double> tmp = new ArrayList<>(len);
                    for (int j = 0; j < len; j++) {
                        Pair<String, String> e = subList.get(j);
                        tmp.add(CosineSimilarity.getSimilarity(e.getKey(), e.getValue()));
                    }
                    return tmp;
                }
            });

            executorService.submit(task);
            result.addAll(task.get());
        }
        executorService.shutdown();
        try {//等待直到所有任务完成
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        started.stop();
        System.out.println(started.elapsed(TimeUnit.MILLISECONDS));

        System.out.println(total);
        System.out.println(result.size());
  /*      184691
        1999000
        1999000*/
    }

    public static String create() {
        String str = null;
        int hightPos, lowPos; // 定义高低位
        Random random = new Random();
        hightPos = (176 + Math.abs(random.nextInt(39)));//获取高位值
        lowPos = (161 + Math.abs(random.nextInt(93)));//获取低位值
        byte[] b = new byte[2];
        b[0] = (new Integer(hightPos).byteValue());
        b[1] = (new Integer(lowPos).byteValue());
        try {
            str = new String(b, "GBk");//转成中文
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
