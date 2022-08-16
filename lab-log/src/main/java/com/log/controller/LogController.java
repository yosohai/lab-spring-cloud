package com.log.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/log")
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @RequestMapping("/test")
    public String testForValue1() {
        String key = "zszxz";
        String value = "知识追寻者";
        logger.debug("The entry is 【{} {}】.", key, value);
        Map<String, String> map = new HashMap();
        map.put(key, value);
        return JSON.toJSONString(map);
    }
}
