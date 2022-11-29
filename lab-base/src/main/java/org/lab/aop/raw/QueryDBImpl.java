package org.lab.aop.raw;

public class QueryDBImpl implements IQueryDB {
  @Override
  public String doQuery() {
    System.out.println("我是查询到的结果哦");
    return "我是查询到的结果哦";
  }
}
