package com.log.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/log")
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public String log(HttpServletRequest request, HttpServletResponse response) {
        MDC.put("requestId", UUID.randomUUID().toString());
        String key = "zszxz";
        String value = "知识追寻者";
        logger.info("The entry is 【{} {}】.", key, value);
        Map<String, String> map = new HashMap();
        map.put(key, value);
        MDC.clear();
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/query2", method = {RequestMethod.GET, RequestMethod.POST})
    public void log2(HttpServletRequest request, HttpServletResponse response) {
        MDC.put("requestId", UUID.randomUUID().toString());
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            logger.error("【response.getWriter()】出错了：", e);
            writer.write("1111");
        }
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            logger.error("【response.getWriter()】出错了：", e);
            writer.write("1/0");
            return;
        } finally {
            writer.close();
        }

        try {
            int i = 2 / 0;
        } catch (Exception e) {
            logger.error("【response.getWriter()】出错了：", e);
            writer.write("2/0");
        }
        MDC.put("requestId", UUID.randomUUID().toString());
    }
}
