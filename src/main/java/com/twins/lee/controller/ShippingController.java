package com.twins.lee.controller;

import com.twins.lee.common.CompanyTool;
import com.twins.lee.entity.Company;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shipping")
public class ShippingController {
    @GetMapping("add")
    public String index() {
        return "/shipping/add";
    }

    @PostMapping("add")
    @ResponseBody
    public Map<String, Object> add() {
        Map<String, Object> map = new HashMap<>();
        Company company = CompanyTool.getCompany();
        company.getId();
        return map;
    }

    @GetMapping("/list")
    public String shippingList() {
        return "/shipping/list";
    }

    public Map<String, Object> getShippingList() {
        Map<String, Object> map = new HashMap<>();

        return map;
    }
}
