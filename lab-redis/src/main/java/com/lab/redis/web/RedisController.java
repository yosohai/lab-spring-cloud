package com.lab.redis.web;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {

    @Resource
    private RedisTemplate redisTemplate;

    // string 入库
    @RequestMapping("/test")
    public void testForValue1() {
        String key = "zszxz";
        String value = "知识追寻者";
        redisTemplate.opsForValue().set(key, value);
    }

    // string 出库
    public void testForValue2() {
        String key = "zszxz";
        Object value = redisTemplate.opsForValue().get(key);
        // 知识追寻者
        System.out.println(value);
    }

    // string key过期时间入库
    public void testForValue3() {
        String key = "today";
        String value = "周六";
        long time = 60;
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }
}
