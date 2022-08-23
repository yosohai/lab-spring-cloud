package com.lab.ws.server;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddSoapHeader extends AbstractSoapInterceptor {

    public static final String xml_namespaceUR_att = "http://server.ws.lab.com//authentication";
    public static final String xml_header_el = "soap:Header";
    public static final String xml_authentication_el = "auth:authentication";
/*	public static final String xml_systemID_el = "auth:systemID";
	public static final String xml_userID_el = "auth:userID";
	public static final String xml_password_el = "auth:password";*/


    public static final String xml_userID_el = "username";
    public static final String xml_password_el = "password";

    public AddSoapHeader() {
        // 指定该拦截器在哪个阶段被激发
        super(Phase.WRITE);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = sd.format(date);
        String userId = "admin";
        String sysId = "1";
        String password = "123456";

        QName qname = new QName(xml_namespaceUR_att, "RequestSOAPHeader");//这个值暂时不清楚具体做什么用，可以随便写

        Document doc = (Document) DOMUtils.createDocument();

        Element child = doc.createElementNS(xml_namespaceUR_att,
                xml_authentication_el);

        Element root = doc.createElement(xml_header_el);
        Element eUserId = doc.createElement(xml_userID_el);
        eUserId.setTextContent(userId);
        Element ePwd = doc.createElement(xml_password_el);
        ePwd.setTextContent(password);
        child.appendChild(eUserId);
        child.appendChild(ePwd);
        root.appendChild(child);
        SoapHeader head = new SoapHeader(qname, root);
        List<Header> headers = message.getHeaders();
        headers.add(head);

    }

}