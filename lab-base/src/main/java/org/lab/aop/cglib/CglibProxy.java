package org.lab.aop.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {
  private Enhancer enhancer = new Enhancer();

  public static void main(String[] args) {
    CglibProxy proxy = new CglibProxy();
    //通过生成子类的方式创建代理类
    QueryDBImpl proxyImp = (QueryDBImpl) proxy.getProxy(QueryDBImpl.class);
    proxyImp.doQuery();
  }

  public Object getProxy(Class clazz) {
    enhancer.setSuperclass(clazz);
    enhancer.setCallback(this);
    return enhancer.create();
  }

  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
    System.out.println("前置代理");
    //通过代理类调用父类中的方法
    Object result = methodProxy.invokeSuper(o, objects);
    System.out.println("后置代理");
    return result;
  }
}
