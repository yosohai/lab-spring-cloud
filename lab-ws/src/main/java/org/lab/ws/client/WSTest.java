package org.lab.ws.client;

import com.alibaba.fastjson.JSONObject;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class WSTest {

    private static final Logger logger = LoggerFactory.getLogger(WSTest.class);

    public static void main(String[] args) {
        logger.info("--------调用webservice接口begin-------");
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();

        //对方的wsdl地址
        Client client = dcf.createClient("http://www.webxml.com.cn/WebServices/TraditionalSimplifiedWebService.asmx?WSDL");
        String json = null;
        try {
            QName qName = new QName("http://webxml.com.cn/", "toTraditionalChinese");                                                //*原文章链接：https://blog.csdn.net/qq_27471405/article/details/105275657     * 其他均为盗版，公众号：灵儿的笔记(zygxsq)
            Object[] objects1 = client.invoke(qName, "副书记扩大飞机失控的"); // 参数1，参数2，参数3......按顺序放就看可以

            json = JSONObject.toJSONString(objects1[0]);
            System.out.println("返回数据:" + json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("服务器断开连接，请稍后再试");
        }
        logger.info("--------调用webservice接口end-------");
    }

    /**
     * 1.代理类工厂的方式,需要拿到对方的接口地址, 同时需要引入接口
     */
//    public static void invokeService_1(){
//        // 接口地址
//        String address = "http://localhost:8080/services/ws/api?wsdl";
//        // 代理工厂
//        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
//        // 设置代理地址
//        jaxWsProxyFactoryBean.setAddress(address);
//        // 设置接口类型
//        jaxWsProxyFactoryBean.setServiceClass(ServerServiceDemo.class);
//        // 创建一个代理接口实现
//        ServerServiceDemo us = (ServerServiceDemo) jaxWsProxyFactoryBean.create();
//        // 数据准备
//        String data = "hello world";
//        // 调用代理接口的方法调用并返回结果
//        String result = us.emrService(data);
//        System.out.println("返回结果:" + result);
//    }

    /**
     * 2. 动态调用
     */
    public static void invokeService_2(){
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8080/services/ws/api?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            //这里注意如果是复杂参数的话，要保证复杂参数可以序列化
            objects = client.invoke("emrService", "hello world");
            System.out.println("返回数据:" + objects[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
