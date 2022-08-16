package com.log;

import com.log.controller.LogController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogApp {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);
    public static void main(String[] args) {
        logger.info("LogApp.main.....");
        SpringApplication.run(LogApp.class);
    }
}