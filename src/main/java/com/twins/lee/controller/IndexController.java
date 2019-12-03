package com.twins.lee.controller;

import com.twins.lee.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Authoe hanyun
 * @Email 1355081829@qq.com
 * @Date 2019/12/3
 **/
@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index/index";
    }

    @GetMapping("/success")
    @ResponseBody
    public Map<String, Object> success() {
        return Response.success("success");
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "/dashboard/index";
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
