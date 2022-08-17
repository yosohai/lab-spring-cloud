package com.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaApp {

    private static final Logger logger = LoggerFactory.getLogger(KafkaApp.class);
    public static void main(String[] args) {
        logger.info("KafkaApp.main.....");
        SpringApplication.run(KafkaApp.class);
    }
}