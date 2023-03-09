package com.chint.similarity.task;

import com.chint.similarity.Demo2;
import com.chint.similarity.text.CosineSimilarity;
import com.chint.similarity.util.ArrayUtil;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class SumTask1 extends RecursiveTask<List<Double>> {


    static final int THRESHOLD = 4000000;
    List<Object> list;
    List<int[]> indexList;
    int start;
    int end;

    SumTask1(List<Object> list, List<int[]> indexList, int start, int end) {
        this.list = list;
        this.indexList = indexList;
        this.start = start;
        this.end = end;
    }

    @Override
    protected List<Double> compute() {
        if (end - start <= THRESHOLD) {
            // 如果任务足够小,直接计算:
            List<Double> tmp = new ArrayList<>(end - start);
            for (int i = start; i < end; i++) {
                int[] e = indexList.get(i);
                tmp.add(CosineSimilarity.getSimilarity((String) list.get(e[0]), (String) list.get(e[1])));
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return tmp;
        }
        // 任务太大,一分为二:
        int middle = (end + start) / 2;
        SumTask1 subtask1 = new SumTask1(this.list, this.indexList, start, middle);
        SumTask1 subtask2 = new SumTask1(this.list, this.indexList, middle, end);
        invokeAll(subtask1, subtask2);
        List<Double> subresult1 = subtask1.join();
        List<Double> subresult2 = subtask2.join();
        subresult1.addAll(subresult2);
        return subresult1;
    }

    public static void main(String[] args) throws Exception {
        // 创建随机数组成的数组:
/*        List<Object> list = new ArrayList<>();
        IntStream.range(0, 10000).boxed().forEach(e -> {
                    list.add("今天小小和妈妈一起去草原里采草莓，今天的草莓味道特别好，而且价格还挺实惠的" + Demo2.create());
                }
        );*/

        Object[] arr = new Object[10000];

        for (int i = 0; i < 10000; i++) {
            arr[i] = "今天小小和妈妈一起去草原里采草莓，今天的草莓味道特别好，而且价格还挺实惠的" + Demo2.create();
        }

        StopWatch stopWatch1 = new StopWatch("段一");
        stopWatch1.start();
        List<int[]> list1 = new ArrayList<>();
        ArrayUtil.combination(arr, 2, list1);
        stopWatch1.stop();
        stopWatch1.start();
        System.out.println(list1.size());
        // fork/join task:
        ForkJoinPool fjp = new ForkJoinPool(13); // 最大并发数4
        ForkJoinTask<List<Double>> task = new SumTask1(Arrays.asList(arr), list1, 0, list1.size());
        List<Double> result = fjp.invoke(task);
        stopWatch1.stop();
        System.out.println("Fork/join sum: " + result.size() + " in " + stopWatch1.prettyPrint() + " ns.");
        fjp.shutdown();
    }

}