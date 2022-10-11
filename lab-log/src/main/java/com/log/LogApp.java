package com.log;

import com.log.controller.LogController;
import com.log.extend.interfaces.MyApplicationContextInitializer1;
import com.log.extend.interfaces.MyBeanDefinitionRegistryPostProcessor2;
import com.log.extend.interfaces.MyBeanFactoryPostProcessor3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

@Import({MyBeanDefinitionRegistryPostProcessor2.class, MyBeanFactoryPostProcessor3.class})
@SpringBootApplication
public class LogApp {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    public static void main(String[] args) {
        logger.info("LogApp.main.....");
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(LogApp.class)
                .bannerMode(Banner.Mode.OFF)
                .logStartupInfo(false);
        /***
         * 因为这时候spring容器还没被初始化，所以想要自己的扩展的生效，有以下三种方式：
         *
         * 在启动类中用springApplication.addInitializers(new TestApplicationContextInitializer())语句加入
         *
         * 配置文件配置context.initializer.classes=com.example.demo.TestApplicationContextInitializer
         *
         * Spring SPI扩展，在spring.factories中加入org.springframework.context.ApplicationContextInitializer=com.example.demo.TestApplicationContextInitializer
         **/
        SpringApplication application = springApplicationBuilder.application();
        application.addInitializers(new MyApplicationContextInitializer1());

        springApplicationBuilder
                .build()
                .run(args);

//        SpringApplication.run(LogApp.class, args);

    }
}