package com.lab.euraka.provider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaProvider {
    public static void main(String[] args) {
        SpringApplication.run(EurekaProvider.class, args);
    }
}