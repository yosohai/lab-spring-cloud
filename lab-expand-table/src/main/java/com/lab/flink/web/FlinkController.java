package com.lab.flink.web;

import com.alibaba.fastjson.JSON;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class FlinkController implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Resource getResource(String location) {
        return resourceLoader.getResource(location);
    }

    @GetMapping("/textFileFlink")
    public String textFileFlink() throws Exception {

        return "File flink over";
    }

    @GetMapping(value = "/")
    public void form(UserForm userForm) throws IOException {
//        return JSON.toJSONString(userForm);
        response.getWriter().write(userForm.toString());
    }
}

@Data
class UserForm {
    private String name;
    private String email;
    private String id;
    private String testeeName;
    private String testeePhone;
    private String testeeSex;
    private String testeeBirth;
    private List<String> list;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}