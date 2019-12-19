package com.twins.lee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Authoe hanyun
 * @Email 1355081829@qq.com
 * @Date 2019/12/19
 **/
@Controller
public class PdfController {
    @GetMapping("/pdf")
    @ResponseBody
    public Map<String, Object> index() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
