package com.lab;
import net.unicon.cas.client.configuration.EnableCasClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCasClient
public class CASClient {
    private static final Logger logger = LoggerFactory.getLogger(CASClient.class);
    public static void main(String[] args) {
        logger.info("CASClient.main.....");
        SpringApplication.run(CASClient.class);
    }
}
