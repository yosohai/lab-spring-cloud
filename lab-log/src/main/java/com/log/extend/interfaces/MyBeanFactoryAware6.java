package com.log.extend.interfaces;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class MyBeanFactoryAware6 implements BeanFactoryAware {
    @Override      
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("[MyBeanFactoryAware6] " + beanFactory.getBean(MyBeanFactoryAware6.class).getClass().getSimpleName());
    }      
}