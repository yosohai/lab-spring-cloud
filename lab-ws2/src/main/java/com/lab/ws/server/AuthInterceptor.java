package com.lab.ws.server;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

/**
 * AuthInterceptor为权限过滤
 *
 * @author Administrator
 */
public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    private SAAJInInterceptor saa = new SAAJInInterceptor();
    private static final String USER_NAME = "admin";
    private static final String USER_PASSWORD = "123456";


    public AuthInterceptor() {
        super(Phase.PRE_PROTOCOL);
        getAfter().add(SAAJInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        SOAPMessage mess = message.getContent(SOAPMessage.class);
        if (mess == null) {
            saa.handleMessage(message);
            mess = message.getContent(SOAPMessage.class);
        }
        SOAPHeader header = null;
        try {
            header = mess.getSOAPHeader();
        } catch (SOAPException e) {
            logger.error("getSOAPheader error:{}", e.getMessage(), e);
            e.printStackTrace();
        }
        if (header == null) {
            throw new Fault(new IllegalAccessException("找不到Header,无法验证用户信息"));
        }
        NodeList username = header.getElementsByTagName("username");
        NodeList password = header.getElementsByTagName("password");
        if (username.getLength() < 1) {
            throw new Fault(new IllegalAccessException("找不到Header,无法验证用户信息"));
        }
        if (password.getLength() < 1) {
            throw new Fault(new IllegalAccessException("找不到Header,无法验证用户信息"));
        }
        String userName = username.item(0).getTextContent().trim();
        String passWord = password.item(0).getTextContent().trim();
        if (USER_NAME.equals(userName) && USER_PASSWORD.equals(passWord)) {
            logger.debug("admin auth success");
        } else {
            SOAPException soapException = new SOAPException("认证错误");
            logger.debug("admin auth failed");
            throw new Fault(soapException);
        }
    }
}



