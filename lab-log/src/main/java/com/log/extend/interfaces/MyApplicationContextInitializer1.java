package com.log.extend.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.UUID;

public class MyApplicationContextInitializer1 implements ApplicationContextInitializer {

    private static final Logger logger = LoggerFactory.getLogger(MyApplicationContextInitializer1.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 获取当前类的 类名
        String clazz = this.getClass().getPackage() + this.getClass().getSimpleName();
        // 获取当前方法
        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        String log_fmt = "【class name:%s】,【method name：%s】info:{}";
        MDC.put("requestId", UUID.randomUUID().toString().replace("-", ""));
        logger.info(String.format(log_fmt, clazz, method), "这是整个spring容器在刷新之前初始化ConfigurableApplicationContext的回调接口，简单来说，就是在容器刷新之前调用此类的initialize方法。这个点允许被用户自己扩展。用户可以在整个spring容器还没被初始化之前做一些事情。\n" + "\n" +
                "可以想到的场景可能为，在最开始激活一些配置，或者利用这时候class还没被类加载器加载的时机，进行动态字节码注入等操作。");
        MDC.clear();
    }
}
