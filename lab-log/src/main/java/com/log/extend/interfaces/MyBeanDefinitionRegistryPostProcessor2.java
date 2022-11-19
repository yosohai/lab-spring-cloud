package com.log.extend.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

public class MyBeanDefinitionRegistryPostProcessor2 implements BeanDefinitionRegistryPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MyBeanDefinitionRegistryPostProcessor2.class);

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        MDC.put("requestId", UUID.randomUUID().toString());
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Foo.class).getBeanDefinition();
        // 获取当前类的 类名
        String clazz = this.getClass().getPackage() + this.getClass().getSimpleName();
        // 获取当前方法
        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        String log_fmt = "【class name:%s】,【method name：%s】info:{}";
        logger.info(String.format(log_fmt, clazz, method), "这个接口在读取项目中的beanDefinition之后执行，提供一个补充的扩展点\n" +
                "使用场景：你可以在这里动态注册自己的beanDefinition，可以加载classpath之外的bean");
        MDC.clear();
        System.out.println("MyBeanDefinitionRegistryPostProcessor2...添加了foo之前的bean数=" + registry.getBeanDefinitionCount());
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Foo.class);
        registry.registerBeanDefinition("helloFoo", rootBeanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // 获取当前类的 类名
        String clazz = this.getClass().getPackage() + this.getClass().getSimpleName();
        // 获取当前方法
        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        String log_fmt = "【class name:%s】,【method name：%s】info:{}";
        MDC.put("requestId", UUID.randomUUID().toString());
        logger.info(String.format(log_fmt, clazz, method), "这个接口在读取项目中的beanDefinition之后执行，提供一个补充的扩展点\n" +
                "使用场景：你可以在这里动态注册自己的beanDefinition，可以加载classpath之外的bean");
        MDC.clear();
        int beanDefinitionCount = configurableListableBeanFactory.getBeanDefinitionCount();
        System.out.println("MyBeanDefinitionRegistryPostProcessor2...postProcessBeanFactory...的bean数=" + beanDefinitionCount);
    }
}

class Foo {

    public Foo() {
        System.out.println("bean的创建");
    }


    @PostConstruct
    public void init() {
        System.out.println("bean的初始化");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("bean的销毁");
    }
}