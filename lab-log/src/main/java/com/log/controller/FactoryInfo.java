package com.log.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wsj
 * @Date 2022/7/19
 */
public enum FactoryInfo {

    /**
     * 浙江正泰太阳能科技有限公司
     */
    HZ01("浙江正泰太阳能科技有限公司", true),
    /**
     * 海宁正泰新能源科技有限公司
     */
    HN01("海宁正泰新能源科技有限公司", true),

    /**
     * 海宁正泰太阳能科技有限公司
     */
    HT01("海宁正泰太阳能科技有限公司", true),

    /**
     * 盐城正泰新能源科技有限公司
     */
    YC01("盐城正泰新能源科技有限公司", true),

    /**
     * 酒泉正泰新能源科技有限公司
     */
    JQ01("酒泉正泰新能源科技有限公司", true),

    /**
     * 乐清正泰太阳能科技有限公司
     */
    YQ01("乐清正泰太阳能科技有限公司", true),

    /**
     * Astronergy(SINGAPORE) PTE
     */
    SG01("Astronergy(SINGAPORE) PTE", false),

    /**
     * M.L.T. Solar Energy
     */
    TH01("M.L.T. Solar Energy", true),

    /**
     * 杭州铮泰进出口贸易有限公司
     */
    ZT01("杭州铮泰进出口贸易有限公司", true),

    /**
     * 松原工厂
     */
    SY01("松原市新能光伏科技有限公司", true),
    ;

    private final String factoryName;
    private final Boolean needExtend;

    FactoryInfo(String factoryName, Boolean needExtend) {
        this.factoryName = factoryName;
        this.needExtend = needExtend;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public Boolean getNeedExtend() {
        return needExtend;
    }

    /**
     * 获取所有生产工厂
     *
     * @return list
     */
    public static List<String> getAllFactory() {
        List<String> list = Arrays.stream(FactoryInfo.values()).map(e -> e.name()
        ).collect(Collectors.toList());
        return list;
    }

    /**
     * 获取原材料需要拓展的工厂
     *
     * @return list
     */
    public static List<String> getExtendFactory() {
        List<String> list = Arrays.stream(FactoryInfo.values()).filter(e -> e.getNeedExtend()).map(e -> e.name()
        ).collect(Collectors.toList());
        return list;
    }

    /**
     * 获取原材料需要拓展的工厂名称
     *
     * @return list
     */
    public static List<String> getExtendFactoryName() {
        List<String> list = Arrays.stream(FactoryInfo.values()).filter(e -> e.getNeedExtend()).map(e -> e.getFactoryName()
        ).collect(Collectors.toList());
        return list;
    }
}
