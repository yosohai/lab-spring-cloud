package com.lab.proguard.web;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tree2")
public class TreeController2 {

    @GetMapping("/treeTest2")
    public List treeTest() {

        // 模拟测试数据（通常为数据库的查询结果）
        final List<NodePO> nodePOs = Arrays.asList(
                new NodePO("1", "一级节点1", null, "_0001"),
                new NodePO("2", "二级节点1.1", "1", "_0002"),
                new NodePO("3", "二级节点1.2", "1", "_0003"),

                new NodePO("4", "一级节点2", null, "_0004"),
                new NodePO("5", "二级节点2.1", "4", "_0005"),
                new NodePO("6", "二级节点2.2", "4", "_0006"),
                new NodePO("7", "三级节点2.2.1", "6", "_0007"),

                new NodePO("8", "一级节点3", null, "_0008"),
                new NodePO("9", "二级节点3.1", "8", "_0009"),
                new NodePO("10", "三级节点3.1.1", "9", "_0010"),
                new NodePO("11", "四级节点3.1.1.1", "10", "_0011"),
                new NodePO("12", "五级节点3.1.1.1.1", "11", "_0012")
        );
        List<Map<String, Object>> mapList = new ArrayList<>();
        nodePOs.stream().forEach(e -> {
            Map<String, Object> userMap = BeanUtil.beanToMap(e);
            mapList.add(userMap);
        });
        return getJava8ResultTree(mapList);
    }

    /**
     * 根据orderNo排序树形结构的每一个层级
     *
     * @param list
     * @return
     */
    public static List<Map<String, Object>> sortJava8Map(List<Map<String, Object>> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        //关键之处，一行代码搞定
        list.sort(Comparator.comparing(m -> String.valueOf(m.get("orderNo"))));
        return list;
    }

    /**
     * 根据父级节点获取所有的子集节点
     *
     * @param parentNode
     * @param allList
     * @return
     */
    public static List<Map<String, Object>> getJava8Children(Map<String, Object> parentNode, List<Map<String, Object>> allList) {
        return allList.stream()
                .filter(curNode -> !ObjectUtils.isEmpty(curNode.get("parentId")) && Objects.equals(curNode.get("parentId"), parentNode.get("id")))
                .peek(m -> m.put("children", getJava8Children(m, allList))).collect(Collectors.toList());
    }

    /**
     * 获取树形结构
     *
     * @param mapList
     * @return treeList 树形结果集
     */
    public static List<Map<String, Object>> getJava8ResultTree(List<Map<String, Object>> mapList) {
        if (CollectionUtils.isEmpty(mapList)) {
            return Lists.newArrayList();
        }
        //filter过滤出所有的一级节点
        return mapList.stream().filter(m -> Objects.equals(m.get("parentId"), null) || Objects.equals(m.get("parentId"), ""))
                .peek(m -> m.put("children", sortJava8Map(getJava8Children(m, mapList)))).collect(Collectors.toList());
    }

}

@Data
@NoArgsConstructor
class NodePO {

    /**
     * 当前节点id
     */
    private String id;

    /**
     * 当前节点名称
     */
    private String name;

    /**
     * 父级节点id
     */
    private String parentId;

    /**
     * 当前节点序号
     */
    private String orderNo;

    /**
     * 子集节点
     */
    private List<NodePO> children;

    /**
     * 构造函数
     *
     * @param id
     * @param name
     * @param parentId
     * @param orderNo
     */
    public NodePO(String id, String name, String parentId, String orderNo) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.orderNo = orderNo;
    }
}
