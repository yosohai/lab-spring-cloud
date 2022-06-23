package com.lab.zk;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ZKConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ZKConsumerApp.class, args);
    }
}
