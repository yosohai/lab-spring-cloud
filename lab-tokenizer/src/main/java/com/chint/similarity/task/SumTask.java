package com.chint.similarity.task;

import cn.hutool.core.lang.Pair;
import com.chint.similarity.Demo2;
import com.chint.similarity.text.CosineSimilarity;
import com.chint.similarity.util.ListUtil;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

class SumTask extends RecursiveTask<List<Double>> {


    static final int THRESHOLD = 4000000;
    List<Pair<String, String>> array;
    int start;
    int end;

    SumTask(List<Pair<String, String>> array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected List<Double> compute() {
        if (end - start <= THRESHOLD) {
            // 如果任务足够小,直接计算:
            List<Double> tmp = new ArrayList<>(end - start);
            for (int i = start; i < end; i++) {
                Pair<String, String> e = array.get(i);
                tmp.add(CosineSimilarity.getSimilarity(e.getKey(), e.getValue()));
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return tmp;
        }
        // 任务太大,一分为二:
        int middle = (end + start) / 2;
        SumTask subtask1 = new SumTask(this.array, start, middle);
        SumTask subtask2 = new SumTask(this.array, middle, end);
        invokeAll(subtask1, subtask2);
        List<Double> subresult1 = subtask1.join();
        List<Double> subresult2 = subtask2.join();
        subresult1.addAll(subresult2);
        return subresult1;
    }

    public static void main(String[] args) throws Exception {
        // 创建随机数组成的数组:
        List<String> list = new ArrayList<>();
        IntStream.range(0, 10000).boxed().forEach(e -> {
                    list.add("今天小小和妈妈一起去草原里采草莓，今天的草莓味道特别好，而且价格还挺实惠的" + Demo2.create());
                }
        );

        StopWatch stopWatch1 = new StopWatch("段一");
        stopWatch1.start();
        List<Pair<String, String>> pairs = ListUtil.concat(list);
        stopWatch1.stop();
        stopWatch1.start();
        System.out.println(pairs.size());
        // fork/join task:
        ForkJoinPool fjp = new ForkJoinPool(2 * 6 + 1); // 最大并发数4
        ForkJoinTask<List<Double>> task = new SumTask(pairs, 0, pairs.size());
        List<Double> result = fjp.invoke(task);
        stopWatch1.stop();
        System.out.println("Fork/join sum: " + result.size() + " in " + stopWatch1.prettyPrint() + " ns.");
        fjp.shutdown();
    }

/*    static final int THRESHOLD = 2000;  IntStream.range(0, 10000)
    49995000
    Fork/join sum: 49995000 in StopWatch '段一': running time = 3106510898900 ns
---------------------------------------------
    ns         %     Task name
---------------------------------------------
        3106510898900  100%    3106.5109s
    ns.*/

}