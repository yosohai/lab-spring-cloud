package org.lab.ws.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "ServerServiceDemo", targetNamespace = "http://server.ws.lab.org")
public interface ServerServiceDemo {
    @WebMethod
    String emrService(@WebParam String data);

}