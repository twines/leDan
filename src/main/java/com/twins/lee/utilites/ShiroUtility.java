package com.twins.lee.utilites;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.List;
import java.util.Map;

public class ShiroUtility {

    public static  boolean isLogin() {
        Subject subject = SecurityUtils.getSubject();
        return subject.getPrincipals().asList().size() > 0;
    }
    public static Map<String, String> casResut() {
        Subject subject = SecurityUtils.getSubject();
        List<Object> result = subject.getPrincipals().asList();
        return (Map<String, String>) result.get(result.size() - 1);
    }
}
