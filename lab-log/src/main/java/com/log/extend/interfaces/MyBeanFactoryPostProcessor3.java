package com.log.extend.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor3 implements BeanFactoryPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MyBeanFactoryPostProcessor3.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 获取当前类的 类名
        String clazz = this.getClass().getPackage() + this.getClass().getSimpleName();
        // 获取当前方法
        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        String log_fmt = "【class name:%s】,【method name：%s】info:{}";
        logger.info(String.format(log_fmt, clazz, method), "这个接口是beanFactory的扩展接口，调用时机在spring在读取beanDefinition信息之后，实例化bean之前。\n" +
                "在这个时机，用户可以通过实现这个扩展接口来自行处理一些东西，比如修改已经注册的beanDefinition的元信息。");
        //获取所有的beanName
        String beanNames[] = beanFactory.getBeanDefinitionNames();
        if (beanNames != null && beanNames.length > 0) {
            BeanDefinition beanDef = null;
            for (String beanName : beanNames) {
                //获取对应的bean定义
                beanDef = beanFactory.getBeanDefinition(beanName);
                this.printBeanDef(beanName, beanDef);
            }
        }
    }

    /**
     * 打印bean定义的基本信息
     *
     * @param beanName
     * @param beanDef
     */
    private void printBeanDef(String beanName, BeanDefinition beanDef) {
        StringBuilder defStr = new StringBuilder("beanName: ").append(beanName);
        defStr.append(", className: ").append(beanDef.getBeanClassName());
        defStr.append(", scope: ").append(beanDef.getScope());
        defStr.append(", parent: ").append(beanDef.getParentName());
        defStr.append(", factoryBean: ").append(beanDef.getFactoryBeanName());
        defStr.append(", factoryMethod: ").append(beanDef.getFactoryMethodName());
        System.out.println(defStr);
    }
}
