package org.lab.thread;

import java.math.BigDecimal;

/**
 * 订单明细
 *
 * @author lzqing
 * @date 2022-07-27
 * @vsrsion 1.0
 **/
public class OrderItem {
    private String articleno; // 条款编号
    private String articlename; // 条款名称
    private String customerfirst; // 开始日期
    private String customerlast; // 结束日期
    private BigDecimal reqqty; // 需求数量
    private String unit; // 单位
    private BigDecimal power; // 每片功率 W/Per
    private BigDecimal totalpower; // 总功率 Total Qty(W)
    private BigDecimal price; // 单价 Price/W

    public String getArticleno() {
        return articleno;
    }

    public void setArticleno(String articleno) {
        this.articleno = articleno;
    }

    public String getArticlename() {
        return articlename;
    }

    public void setArticlename(String articlename) {
        this.articlename = articlename;
    }

    public String getCustomerfirst() {
        return customerfirst;
    }

    public void setCustomerfirst(String customerfirst) {
        this.customerfirst = customerfirst;
    }

    public String getCustomerlast() {
        return customerlast;
    }

    public void setCustomerlast(String customerlast) {
        this.customerlast = customerlast;
    }

    public BigDecimal getReqqty() {
        return reqqty;
    }

    public void setReqqty(BigDecimal reqqty) {
        this.reqqty = reqqty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public BigDecimal getTotalpower() {
        return totalpower;
    }

    public void setTotalpower(BigDecimal totalpower) {
        this.totalpower = totalpower;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "订单明细：" +
                "条款编号='" + articleno + '\'' +
                ", 条款名称='" + articlename + '\'' +
                ", 开始日期='" + customerfirst + '\'' +
                ", 结束日期='" + customerlast + '\'' +
                ", 需求数量=" + reqqty +
                ", 单位='" + unit + '\'' +
                ", 每片功率=" + power +
                ", 总功率=" + totalpower +
                ", 单价=" + price;
    }
}
