package com.twins.lee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/shipping")
public class ShippingController {
    @GetMapping("add")
    public String index() {
        return "/shipping/add";
    }
}
