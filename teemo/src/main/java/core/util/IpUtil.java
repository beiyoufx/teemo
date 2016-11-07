/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yongjie.teng
 * @version 1.0
 * @date 16-10-21
 * @email yongjie.teng@foxmail.com
 * @package core.util
 * @project teemo
 */
public class IpUtil {

    /**
     * 从Spring上下文中获取Request，然后从Request对象中获取IP
     * @return {@link String}
     */
    public static String getIp() {
        RequestAttributes requestAttributes = null;

        try {
            requestAttributes = RequestContextHolder.currentRequestAttributes();
        } catch (Exception e) {
            //ignore  如unit test
        }

        if (requestAttributes != null && requestAttributes instanceof ServletRequestAttributes) {
            return getIpAddr(((ServletRequestAttributes) requestAttributes).getRequest());
        }

        return "unknown";
    }

    /**
     * 从Request获取IP
     * 如果请求时通过反向代理过来的，需要在从代理字段中获取真实IP
     * @param request
     * @return {@link String}
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}