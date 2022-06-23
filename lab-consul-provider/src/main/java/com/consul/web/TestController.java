package com.consul.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TestController {
    @RequestMapping(value = "/test", method = {RequestMethod.GET})
    public String test() {
        return UUID.randomUUID().toString();
    }
}
