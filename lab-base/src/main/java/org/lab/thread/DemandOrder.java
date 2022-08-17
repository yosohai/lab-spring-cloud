package org.lab.thread;

import cn.hutool.json.JSONUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 客户订单
 *
 * @author lzqing
 * @date 2022-07-27
 * @vsrsion 1.0
 **/
public class DemandOrder {

    private String demand; // DO号码
    private String project; // 订单名称
    private String customer; // 客户编号
    private String customername; // 客户名称
    private String salesoffice; // 销售组织编号
    private String salesofficename; // 销售组织名称
    private String salesgroup; // 销售组编号
    private String salesgroupname; // 销售组名称
    private String csr; // 销售人员
    private List<OrderItem> itemlist;

    public DemandOrder() {
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getSalesoffice() {
        return salesoffice;
    }

    public void setSalesoffice(String salesoffice) {
        this.salesoffice = salesoffice;
    }

    public String getSalesofficename() {
        return salesofficename;
    }

    public void setSalesofficename(String salesofficename) {
        this.salesofficename = salesofficename;
    }

    public String getSalesgroup() {
        return salesgroup;
    }

    public void setSalesgroup(String salesgroup) {
        this.salesgroup = salesgroup;
    }

    public String getSalesgroupname() {
        return salesgroupname;
    }

    public void setSalesgroupname(String salesgroupname) {
        this.salesgroupname = salesgroupname;
    }

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }

    public List<OrderItem> getItemlist() {
        return itemlist;
    }

    public void setItemlist(List<OrderItem> itemlist) {
        this.itemlist = itemlist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DemandOrder that = (DemandOrder) o;
        return demand.equals(that.demand) && project.equals(that.project) && customer.equals(that.customer) && customername.equals(that.customername) && salesoffice.equals(that.salesoffice) && salesofficename.equals(that.salesofficename) && salesgroup.equals(that.salesgroup) && salesgroupname.equals(that.salesgroupname) && csr.equals(that.csr) && itemlist.equals(that.itemlist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(demand, project, customer, customername, salesoffice, salesofficename, salesgroup, salesgroupname, csr, itemlist);
    }


    @Override
    public String toString() {
        return "客户订单:" +
                "DO编号='" + demand + '\'' +
                ", 订单名称='" + project + '\'' +
                ", 客户编号='" + customer + '\'' +
                ", 客户='" + customername + '\'' +
                ", 销售组织编号='" + salesoffice + '\'' +
                ", 销售组织='" + salesofficename + '\'' +
                ", 销售组编号='" + salesgroup + '\'' +
                ", 销售组='" + salesgroupname + '\'' +
                ", 销售人员='" + csr + '\'' +
                ", 订单明细：" + itemlist;
    }

    public static void main(String[] args) {
        DemandOrder demandOrder = new DemandOrder();
        demandOrder.setDemand("1");
        demandOrder.setCsr("1");
        demandOrder.setCustomer("1");
        demandOrder.setCustomername("1");
        demandOrder.setProject("1");
        demandOrder.setSalesgroup("1");
        demandOrder.setSalesgroupname("1");
        demandOrder.setSalesoffice("1");
        demandOrder.setSalesofficename("1");
        List<OrderItem> list = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setArticlename("2");
        orderItem.setArticleno("2");
        orderItem.setCustomerfirst("2");
        orderItem.setCustomerlast("2");
        orderItem.setPower(new BigDecimal("2.2"));
        orderItem.setPrice(new BigDecimal("2.2"));
        orderItem.setReqqty(new BigDecimal("2.2"));
        orderItem.setTotalpower(new BigDecimal("2.2"));
        orderItem.setUnit("pc");
        list.add(orderItem);
        demandOrder.setItemlist(list);
        System.out.println(JSONUtil.toJsonStr(demandOrder));
    }
}
