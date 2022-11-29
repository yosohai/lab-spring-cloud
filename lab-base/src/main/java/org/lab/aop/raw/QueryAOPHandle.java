package org.lab.aop.raw;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class QueryAOPHandle implements InvocationHandler {
  private Object obj;

  public QueryAOPHandle() {
  }

  public QueryAOPHandle(Object obj) {
    this.obj = obj;
  }

  public static void main(String[] args) {
    QueryDBImpl queryDBImpl = new QueryDBImpl();
    QueryAOPHandle handle = new QueryAOPHandle(queryDBImpl);
    IQueryDB iqueryDB = (IQueryDB) Proxy.newProxyInstance(QueryDBImpl.class.getClassLoader(), new Class[]{IQueryDB.class}, handle);
    iqueryDB.doQuery();
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    //方法返回值
    System.out.println("前置代理");
    //反射调用方法
    Object ret = method.invoke(obj, args);
    //声明结束
    System.out.println("后置代理");
    //返回反射调用方法的返回值
    return ret;
  }
}
