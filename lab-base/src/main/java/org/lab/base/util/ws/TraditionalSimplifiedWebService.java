package org.lab.base.util.ws;

import cn.hutool.http.webservice.SoapClient;

/**
 * 简繁互换
 *
 * @author lzqing
 * @date 2022-07-22
 * @vsrsion 1.0
 **/
public class TraditionalSimplifiedWebService {

    public static void main(String[] args) throws Exception {
        SoapClient soapClient = SoapClient
                .create("http://www.webxml.com.cn/WebServices/TraditionalSimplifiedWebService.asmx?WSDL")
                .setMethod("toTraditionalChinese", "http://webxml.com.cn/");
        soapClient.getMethodEle().addChildElement("sText").setValue("中国年");
        System.out.println(soapClient.getMsgStr(true));
        System.out.println(soapClient.send(true));
    }
}
