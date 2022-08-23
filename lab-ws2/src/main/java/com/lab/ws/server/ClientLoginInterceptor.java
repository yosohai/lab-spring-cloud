package com.lab.ws.server;

import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 定义拦截器用户用户验证
 * @author Administrator
 *
 */

public class ClientLoginInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
    private String username;
    private String password;
    
    public ClientLoginInterceptor() {
    	// 定义在哪个阶段进行拦截
        super(Phase.PREPARE_SEND);
    }
    public ClientLoginInterceptor(String username, String password) {
        //super();
    	// 定义在哪个阶段进行拦截
        super(Phase.PREPARE_SEND);
        this.username = username;
        this.password = password;
    }

    @Override
    public void handleMessage(SoapMessage soap) throws Fault {
        List<Header> headers = soap.getHeaders();
        Document doc = DOMUtils.createDocument();

//        Element auth = doc.createElement("authrity");
        Element auth = doc.createElementNS("http://server.ws.lab.com","UserService"); // WebServices：命名空间
        Element username = doc.createElement("username");
        Element password = doc.createElement("password");
        username.setTextContent(this.username);
        password.setTextContent(this.password);
        auth.appendChild(username);
        auth.appendChild(password);
        headers.add(0, new Header(new QName("identification"), auth));
    }
}

