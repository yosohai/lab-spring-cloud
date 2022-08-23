package com.lab;

import com.lab.ws.server.AddSoapHeader;
import com.lab.ws.server.AuthInterceptor;
import com.lab.ws.server.ClientLoginInterceptor;
import com.lab.ws.server.UserService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class WS2App {
    private static final Logger logger = LoggerFactory.getLogger(WS2App.class);

    public static void main(String[] args) {
        logger.info("WS2App.main.....");
        SpringApplication.run(WS2App.class);
        getUserTest1();
//        getUserNameTest2();
    }

    /**
     * 代理工厂的方式，需要拿到对方的接口地址
     */
    public static void getUserTest1() {
        // 接口地址
        String address = "http://admin:123456@localhost:8080/services/user?wsdl";
        // 代理工厂
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        AddSoapHeader ash = new AddSoapHeader();
        ArrayList list = new ArrayList();
        // 添加soap header 信息
        list.add(ash);
        //注入拦截器，getOutInterceptors代表调用服务端时触发,getInInterceptors就是被调用才触发
        jaxWsProxyFactoryBean.setOutInterceptors(list);
        jaxWsProxyFactoryBean.setUsername("admin");
        jaxWsProxyFactoryBean.setPassword("123456");
        // 设置代理地址
        jaxWsProxyFactoryBean.setAddress(address);
        // 设置接口类型
        jaxWsProxyFactoryBean.setServiceClass(UserService.class);
        // 创建一个代理接口实现
        UserService us = (UserService) jaxWsProxyFactoryBean.create();
        //测试数据
        int id = 2;
        // 调用代理接口的方法调用并返回结果
        String userName = us.getUserName(id);
        System.out.println("返回结果: " + userName);
    }

    /**
     * 动态调用
     */
    public static void getUserNameTest2() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();

        Client client = dcf.createClient("http://localhost:8080/services/user?wsdl");
        // 需要密码的情况需要加上用户名和密码
        client.getOutInterceptors().add(new ClientLoginInterceptor("admin", "123456"));

        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("getUserName", 2);
            System.out.println("返回数据:" + objects[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
