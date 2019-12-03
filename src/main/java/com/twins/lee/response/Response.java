package com.twins.lee.response;

import java.util.HashMap;
import java.util.Map;

/**
 * @Authoe hanyun
 * @Email 1355081829@qq.com
 * @Date 2019/12/3
 **/
public class Response {

    public static Map<String, Object> success(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        map.put("data", null);
        map.put("code", 0);

        return map;
    }

    public static Map<String, Object> success(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "success");
        map.put("data", data);
        map.put("code", 0);
        return map;
    }

    public static Map<String, Object> success(Object data, String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        map.put("data", data);
        map.put("code", 0);
        return map;
    }

    public static Map<String, Object> error(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        map.put("data", null);
        map.put("code", 1);
        return map;
    }

    public static Map<String, Object> error(String msg, int code) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        map.put("data", null);
        map.put("code", code);
        return map;
    }

}
