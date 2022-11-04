package org.lab.base.util;

import cn.hutool.core.text.UnicodeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UnicodeUtilTest {
    public static void main(String[] args) {
        //第二个参数true表示跳过ASCII字符（只跳过可见字符）
        String s = UnicodeUtil.toUnicode("aaa123中文", true);
        System.out.println(s);
        s = "aaa123\\u4e2d\\u6587";
        System.out.println(UnicodeUtil.toString(s));
        System.out.println(UnicodeUtil.toString("中午"));
        List<LineProps> list = new ArrayList<>();
        list.add(new LineProps("part1", 1L));
        list.add(new LineProps("part2", 2L));
        list.add(new LineProps("part3", 3L));
        list.add(new LineProps("part1", 11L));
        list.add(new LineProps("part2", 22L));
        list.add(new LineProps("part3", 33L));
        list.add(new LineProps("part4", 4L));
        list.add(new LineProps("part5", 1L));
        list.add(new LineProps("part2", 222L));
        list.add(new LineProps("part1", 111L));
        Map<String, List<LineProps>> propsMap = list.stream().collect(Collectors.groupingBy(item -> item.getPartNo()));
        System.out.println(propsMap);
        StringBuilder builder = new StringBuilder();
        builder.append("项目文本1,2合计超出40字符: ");
        int i = 0;
        int size = propsMap.size();
        for (Map.Entry<String, List<LineProps>> entry : propsMap.entrySet()) {
            builder.append(entry.getKey());
            entry.getValue().stream().forEach(e -> builder.append("行号").append(e.getLineNo()).append(","));
            if (i < size - 1) {
                builder.replace(builder.length() - 1, builder.length(), ";");
            } else {
                builder.replace(builder.length() - 1, builder.length(), "");
            }
            i++;
        }
        System.out.println(builder.toString());
    }
}

/**
 * 物料的行属性
 */
class LineProps {
    private String partNo;
    private Long lineNo;

    public LineProps() {
    }

    public LineProps(String partNo, Long lineNo) {
        this.partNo = partNo;
        this.lineNo = lineNo;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public Long getLineNo() {
        return lineNo;
    }

    public void setLineNo(Long lineNo) {
        this.lineNo = lineNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineProps lineProps = (LineProps) o;
        return Objects.equals(partNo, lineProps.partNo) && Objects.equals(lineNo, lineProps.lineNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partNo, lineNo);
    }

    @Override
    public String toString() {
        return "LineProps{" +
                "partNo='" + partNo + '\'' +
                ", lineNo='" + lineNo + '\'' +
                '}';
    }


}
