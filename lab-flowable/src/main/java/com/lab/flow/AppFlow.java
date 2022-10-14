package com.lab.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppFlow {

    private static final Logger logger = LoggerFactory.getLogger(AppFlow.class);
    public static void main(String[] args) {
        SpringApplication.run(AppFlow.class, args);
    }
}