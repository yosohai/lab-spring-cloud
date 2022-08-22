package com.lab.ws.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * WebService接口
 * WebService(targetNamespace = "http://server.ws.lab.com")
 * 如果不添加的话,动态调用invoke的时候,会报找不到接口内的方法
 */
@WebService(targetNamespace = "http://server.ws.lab.com")
public interface UserService {

    @WebMethod  //标注该方法为webservice暴露的方法,用于向外公布，它修饰的方法是webservice方法
    String getUser(@WebParam(name = "id") int id);

    @WebMethod
    @WebResult(name = "String", targetNamespace = "")
    String getUserName(@WebParam(name = "id") int id);
}

