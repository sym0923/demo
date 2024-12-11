package com.zhide.codegenerate.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;

/**
 * http相关工具类
 */
public class HttpUtils {
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes==null? null : requestAttributes.getRequest();
    }
    public static HttpServletResponse getResponse(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes==null? null : requestAttributes.getResponse();
    }

    /**
     * 判断是否是文件上传
     * @param ctx
     * @return
     */
    public static final boolean isMultipartContent(HttpServletRequest ctx) {
        String contentType = ctx.getContentType();
        if (contentType == null) {
            return false;
        } else {
            return contentType.toLowerCase(Locale.ENGLISH).startsWith("multipart/");
        }
    }
    /**
     * 获取请求ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        String SIGN = ",";
        String STR_UNKNOWN = "unknown";

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || STR_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || STR_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || STR_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || STR_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || STR_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip.contains(SIGN)) {
            return ip.split(SIGN)[0];
        } else {
            return ip;
        }
    }
}
