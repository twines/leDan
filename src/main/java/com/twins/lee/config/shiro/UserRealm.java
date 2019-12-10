package com.twins.lee.config.shiro;

import com.twins.lee.utilites.Utility;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class UserRealm extends CasRealm {
    @Resource
    ShiroCasConfiguration shiroCasConfiguration;

    @PostConstruct
    public void initProperty(){
//      setDefaultRoles("ROLE_USER");
        setCasServerUrlPrefix(shiroCasConfiguration.casServerUrlPrefix);
        // 客户端回调地址
        setCasService(shiroCasConfiguration.shiroServerUrlPrefix + shiroCasConfiguration.casFilterUrlPattern);
    }
    /*
    授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

//TODO 暂时由于系统没有人员权限问题
        return authorizationInfo;
       /*
        User user = (User) principalCollection.getPrimaryPrincipal();
        List<String> permissionsSet = new ArrayList<>();
        //默认使用null不进行权限控制
        for (Role role : user.getRoles()) {
            authorizationInfo.addRole(role.getRole());
            List<Permission> permissions = roleMapper.getRoleById(role.getId()).getPermissions();
            for (Permission permission : permissions) {
                authorizationInfo.addStringPermission(permission.getPermission());
             }
        }
        authorizationInfo.addStringPermissions(permissionsSet);

        return authorizationInfo;

        */
    }




    /*
    认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CasToken casToken = (CasToken) token;
        if (token == null) {
            return null;
        }

        String ticket = (String)casToken.getCredentials();
        if (!StringUtils.hasText(ticket)) {
            return null;
        }

        //ticket检验器
        TicketValidator ticketValidator = ensureTicketValidator();

        try {
            // 去CAS服务端中验证ticket的合法性
            Assertion casAssertion = ticketValidator.validate(ticket, getCasService());
            // 从CAS服务端中获取相关属性,包括用户名、是否设置RememberMe等
            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
            String userId = casPrincipal.getName();

            Map<String, Object> attributes = casPrincipal.getAttributes();
            // refresh authentication token (user id + remember me)
            casToken.setUserId(userId);
            String rememberMeAttributeName = getRememberMeAttributeName();
            String rememberMeStringValue = (String)attributes.get(rememberMeAttributeName);
            boolean isRemembered = rememberMeStringValue != null && Boolean.parseBoolean(rememberMeStringValue);
            if (isRemembered) {
                casToken.setRememberMe(true);
            }
            // 最终创建SimpleAuthencationInfo实体返回给SecurityManager
            List<Object> principals = CollectionUtils.asList(userId, Utility.decodeLeDanInfo( attributes));
            PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());
            return new SimpleAuthenticationInfo(principalCollection, ticket);
        } catch (TicketValidationException | UnsupportedEncodingException e) {
            throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
        }
    }
}
