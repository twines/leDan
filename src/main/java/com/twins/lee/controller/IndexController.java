package com.twins.lee.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.twins.lee.common.CompanyTool;
import com.twins.lee.entity.Company;
import com.twins.lee.entity.Shipping;
import com.twins.lee.mapper.CompanyMapper;
import com.twins.lee.mapper.ShippingMapper;
import com.twins.lee.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * @Authoe hanyun
 * @Email 1355081829@qq.com
 * @Date 2019/12/3
 **/
@Controller
public class IndexController {
    @Autowired
    private ShippingMapper shippingMapper;
    @Autowired
    private CompanyMapper companyMapper;

    @GetMapping("/")
    public String index() {
        return "index/index";
    }


    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("/dashboard/index");
        int companyAuth = companyMapper.selectCount(new QueryWrapper<Company>().eq("user_id", CompanyTool.getCompany().getUserId()));
        int shippingNumber = shippingMapper.selectCount(new QueryWrapper<Shipping>().eq("type", 1));
        modelAndView.addObject("shippingNumber", shippingNumber);
        modelAndView.addObject("company", companyAuth);
        return modelAndView;
    }

    @GetMapping("/success")
    @ResponseBody
    public Map<String, Object> success() {
        return Response.success("success");
    }

    @GetMapping("/success/object")
    @ResponseBody
    public Map<String, Object> successObject() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1111);
        return Response.success(map);
    }

    @GetMapping("/success/object/msg")
    @ResponseBody
    public Map<String, Object> successObjectMsg() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1111);
        return Response.success(map, "hhhhh");
    }
}
