package com.lab.cas.config;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @Author: lzqing
 * @Date: 2022-08-24
 * @Description: CAS集成核心配置类
 */
@Configuration
@Slf4j
@ConditionalOnProperty(value = "cas.loginType", havingValue = "cas")
@Data
public class CasFilterConfig {

    /**
     * 需要走cas拦截的地址（/* 所有地址都拦截）
     */
    @Value("${cas.urlPattern:/casLogin}")
    private String filterUrl;

    /**
     * 默认的cas地址，防止通过 配置信息获取不到
     */
    @Value("${cas.server-url-prefix:http://cas.server.com:8443/cas}")
    private String casServerUrl;

    /**
     * 应用访问地址（这个地址需要在cas服务端进行配置）
     */
    @Value("${cas.authentication-url:http://localhost:8090}")
    private String authenticationUrl;

    /**
     * 应用访问地址（这个地址需要在cas服务端进行配置）
     */
    @Value("${cas.client-host-url:http://localhost:8090}")
    private String appServerUrl;

    /**
     * 决定票据验证过滤器的版本，默认30，old是20版
     */
    @Value("${cas.filterVersion:new}")
    private String filterVersion;

    public CasFilterConfig() {
    }

    public static Logger getLog() {
        return log;
    }

    public String getFilterUrl() {
        return filterUrl;
    }

    public void setFilterUrl(String filterUrl) {
        this.filterUrl = filterUrl;
    }

    public String getCasServerUrl() {
        return casServerUrl;
    }

    public void setCasServerUrl(String casServerUrl) {
        this.casServerUrl = casServerUrl;
    }

    public String getAuthenticationUrl() {
        return authenticationUrl;
    }

    public void setAuthenticationUrl(String authenticationUrl) {
        this.authenticationUrl = authenticationUrl;
    }

    public String getAppServerUrl() {
        return appServerUrl;
    }

    public void setAppServerUrl(String appServerUrl) {
        this.appServerUrl = appServerUrl;
    }

    public String getFilterVersion() {
        return filterVersion;
    }

    public void setFilterVersion(String filterVersion) {
        this.filterVersion = filterVersion;
    }

    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        log.info(" \n cas 单点登录配置 \n appServerUrl = " + appServerUrl + "\n casServerUrl = " + casServerUrl);
        log.info(" servletListenerRegistrationBean ");
        ServletListenerRegistrationBean listenerRegistrationBean = new ServletListenerRegistrationBean();
        listenerRegistrationBean.setListener(new SingleSignOutHttpSessionListener());
        listenerRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return listenerRegistrationBean;
    }

    /**
     * 单点登录退出
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        log.info(" servletListenerRegistrationBean ");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new SingleSignOutFilter());
        registrationBean.addUrlPatterns(filterUrl);
        registrationBean.addInitParameter("casServerUrlPrefix", casServerUrl);
        registrationBean.setName("CAS Single Sign Out Filter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    /**
     * 单点登录认证
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean AuthenticationFilter() {
        log.info(" AuthenticationFilter ");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new AuthenticationFilter());
        registrationBean.addUrlPatterns(filterUrl);
        registrationBean.setName("CAS Filter");
        registrationBean.addInitParameter("casServerLoginUrl", casServerUrl);
        registrationBean.addInitParameter("serverName", appServerUrl);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    /**
     * 单点登录校验
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean Cas30ProxyReceivingTicketValidationFilter() {
        log.info(" Cas30ProxyReceivingTicketValidationFilter ");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        if (StrUtil.isNotBlank(filterVersion) && filterVersion.equals("old")) {
            log.info(" Cas20ProxyReceivingTicketValidationFilter ");
            registrationBean.setFilter(new Cas20ProxyReceivingTicketValidationFilter());
        } else {
            log.info(" Cas30ProxyReceivingTicketValidationFilter ");
            registrationBean.setFilter(new Cas30ProxyReceivingTicketValidationFilter());
        }
        registrationBean.addUrlPatterns(filterUrl);
        registrationBean.setName("CAS Validation Filter");
        registrationBean.addInitParameter("casServerUrlPrefix", authenticationUrl);
        registrationBean.addInitParameter("serverName", appServerUrl);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    /**
     * 单点登录请求包装
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean httpServletRequestWrapperFilter() {
        log.info(" httpServletRequestWrapperFilter ");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new HttpServletRequestWrapperFilter());
        registrationBean.addUrlPatterns(filterUrl);
        registrationBean.setName("CAS HttpServletRequest Wrapper Filter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}

