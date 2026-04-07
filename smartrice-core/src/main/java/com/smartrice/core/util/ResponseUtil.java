package com.smartrice.core.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static Object ok() {
        return ok(null);
    }

    public static Object ok(Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("errno", 0);
        result.put("errmsg", "成功");
        result.put("data", data);
        return result;
    }

    public static Object fail(int errno, String errmsg) {
        Map<String, Object> result = new HashMap<>();
        result.put("errno", errno);
        result.put("errmsg", errmsg);
        return result;
    }

    public static Object badArgument() {
        return fail(401, "参数不对");
    }

    public static Object badArgumentValue() {
        return fail(402, "参数值不对");
    }

    public static Object unlogin() {
        return fail(501, "请登录");
    }

    public static Object unauthz() {
        return fail(506, "无操作权限");
    }

    public static Object updatedDateExpired() {
        return fail(507, "数据已过期");
    }

    public static Object serious() {
        return fail(502, "系统内部错误");
    }
}
