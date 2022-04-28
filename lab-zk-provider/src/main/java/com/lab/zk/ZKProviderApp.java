package com.lab.zk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ZKProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(ZKProviderApp.class, args);
    }
}
