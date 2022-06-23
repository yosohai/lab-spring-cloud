package com.consul.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class ConsulController {
    private final String url = "http://client-provider";
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer")
    public String getUUID() {
        return restTemplate.getForObject(url + "/test", String.class);
    }
}
