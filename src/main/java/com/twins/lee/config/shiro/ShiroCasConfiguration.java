package com.twins.lee.config.shiro;

import org.apache.shiro.cas.CasFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroCasConfiguration {


    // CasServerUrlPrefix
    @Value("${twins.cas-server-url-prefix}")
    public String casServerUrlPrefix = " http://testld.ldforms.com:88/cas";
    // Cas登录页面地址
    @Value("${twins.cas-login-url}")
    public String casLoginUrl = casServerUrlPrefix + "/login";
    // Cas登出页面地址
    @Value("${twins.cas-logout-url}")
    public String casLogoutUrl = casServerUrlPrefix + "/logout";
    // 当前工程对外提供的服务地址
    @Value("${twins.shiro-server-url-prefix}")
    public String shiroServerUrlPrefix = "http://localhost:8080";
    // casFilter UrlPattern
    @Value("${twins.case-filter-url-patter}")
    public String casFilterUrlPattern = "/cas";
    // 登录地址
    @Value("${twins.login-url}")
    public String loginUrl = casLoginUrl + "?service=" + shiroServerUrlPrefix + casFilterUrlPattern;

    /**
     * CAS过滤器
     *
     * @return
     * @author SHANHY
     * @create 2016年1月17日
     */
    @Bean(name = "casFilter")
    public CasFilter getCasFilter() {

        CasFilter casFilter = new CasFilter();
        casFilter.setName("casFilter");
        casFilter.setEnabled(true);
        // 登录失败后跳转的URL，也就是 Shiro 执行 CasRealm 的 doGetAuthenticationInfo 方法向CasServer验证tiket
        casFilter.setFailureUrl(loginUrl);// 我们选择认证失败后再打开登录页面
        casFilter.setSuccessUrl(loginUrl);
        return casFilter;
    }
}
