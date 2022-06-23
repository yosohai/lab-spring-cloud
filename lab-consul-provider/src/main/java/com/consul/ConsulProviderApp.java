package com.consul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsulProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsulProviderApp.class, args);
    }
}
