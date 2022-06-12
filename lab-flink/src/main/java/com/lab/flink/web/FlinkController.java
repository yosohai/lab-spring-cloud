package com.lab.flink.web;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.LocalEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlinkController implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Resource getResource(String location) {
        return resourceLoader.getResource(location);
    }

    @GetMapping("/textFileFlink")
    public String textFileFlink() throws Exception {
        // 1.创建执行环境
        LocalEnvironment localEnvironment = ExecutionEnvironment.createLocalEnvironment();

        // 2.读取文件
        Resource resource = resourceLoader.getResource("classpath:resources/word.txt");

        String path = ResourceUtils.getURL("classpath:").getPath();
        DataSource<String> dataSource = localEnvironment.readTextFile(path + "word.txt");
        // 3.每行数据分词 转换成二元组类型
        FlatMapOperator<String, Tuple2<String, Long>> operator = dataSource.flatMap((String line, Collector<Tuple2<String, Long>> collector) -> {
            String[] words = line.split(" ");
            for (String word : words) {
                collector.collect(Tuple2.of(word, 1L));
            }

        }).returns(Types.TUPLE(Types.STRING, Types.LONG));

        // 4. 按照word分组
        UnsortedGrouping<Tuple2<String, Long>> groupBy = operator.groupBy(0);
        // 5. 分组聚合统计
        AggregateOperator<Tuple2<String, Long>> aggregateOperator = groupBy.sum(1);

        aggregateOperator.print();
        return "File flink over";
    }

    @GetMapping("/stockFlink")
    public String stockFlink() throws Exception {
        // 1.创建流式执行环境
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        // 2.读取文本流  nc -lk 7777开启socket 7777 端口
        DataStreamSource<String> dataStreamSource = environment.socketTextStream("124.221.86.80", 7777);

        // 3.转换执行
        SingleOutputStreamOperator<Tuple2<String, Long>> operator = dataStreamSource.flatMap((String line, Collector<Tuple2<String, Long>> out) -> {
            String[] words = line.split(" ");
            for (String word : words) {
                out.collect(Tuple2.of(word, 1L));
            }
        }).returns(Types.TUPLE(Types.STRING, Types.LONG));

        // 4.分组
        KeyedStream<Tuple2<String, Long>, String> keyedStream = operator.keyBy(data -> data.f0);

        // 5.求和
        SingleOutputStreamOperator<Tuple2<String, Long>> sum = keyedStream.sum(1);

        // 6.打印
        sum.print();

        // 7.执行
        environment.execute();
        return "Stock flink over";
    }
}
