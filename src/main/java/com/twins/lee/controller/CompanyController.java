package com.twins.lee.controller;

import com.twins.lee.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/company")
public class CompanyController {
    @GetMapping("/auth")
    public String auth() {
        return "/company/auth";
    }

    @PostMapping("/doAuth")
    @ResponseBody
    public Map<String, Object> aoAuth() {
        Map<String, Object> map = new HashMap<>();
        return Response.success(map);
    }
}
