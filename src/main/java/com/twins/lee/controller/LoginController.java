package com.twins.lee.controller;

import com.twins.lee.config.shiro.ShiroCasConfiguration;
import com.twins.lee.mapper.CompanyMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    CompanyMapper companyMapper;

    @Resource
    ShiroCasConfiguration shiroCasConfiguration;
    @Value("${environment}")
    private String environment;

    @GetMapping("/cas")
    public String casTicket(@RequestParam("ticket") String ticket) throws UnsupportedEncodingException {
        Object value = null;
        CasToken casToken = new CasToken(ticket);
        casToken.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        subject.login(casToken);
        List list = subject.getPrincipals().asList();
        System.out.println(list);
        value = subject.getPrincipal();
        subject.getSession().setAttribute("user", value);

//        if (ShiroUtility.isLogin()) {
//            Map<String, String> userInfo = ShiroUtility.casResut();
//            Long userId = Long.valueOf(userInfo.get("id"));
//          Improv improv =  improvService.UserImproveResultById(userId);
//            if (improv !=null  && improv.getState() != Improv.State.NeededInproved) {
//                return "redirect:/";
//            }
//        }
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String index() {
        return "redirect:" + shiroCasConfiguration.loginUrl;
    }




}
