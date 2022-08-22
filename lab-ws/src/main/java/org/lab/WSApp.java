package org.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WSApp {
    private static final Logger logger = LoggerFactory.getLogger(WSApp.class);
    public static void main(String[] args) {
        logger.info("WSApp.main.....");
        SpringApplication.run(WSApp.class);
    }
}
