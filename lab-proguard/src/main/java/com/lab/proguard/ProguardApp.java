package com.lab.proguard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProguardApp {

    private static final Logger logger = LoggerFactory.getLogger(ProguardApp.class);

    public static void main(String[] args) {
        logger.info("ProguardApp.main.....");
        SpringApplication.run(ProguardApp.class, args);

    }
}