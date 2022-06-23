package com.lab.flink;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class App {
    public static void main(String[] args) throws Exception {
        // 1.创建流式执行环境
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        // 2.读取文本流
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

    }
}
