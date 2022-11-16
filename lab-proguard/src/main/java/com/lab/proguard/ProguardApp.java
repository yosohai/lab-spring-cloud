package com.lab.proguard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class ProguardApp {

    private static final Logger logger = LoggerFactory.getLogger(ProguardApp.class);

    public static void main(String[] args) {
        logger.info("LogApp.main.....");
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(ProguardApp.class)
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

        springApplicationBuilder
                .build()
                .run(args);

//        SpringApplication.run(LogApp.class, args);

    }
}