/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.teemo.core.Constants;
import com.teemo.core.util.UserLogUtil;
import com.teemo.dto.Result;
import com.teemo.entity.User;
import core.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yongjie.teng
 * @date 16-12-2 下午5:15
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.web.controller
 */
@ControllerAdvice
public class DefaultExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void resolveUnauthorizedException(HttpServletRequest request, HttpServletResponse response, UnauthorizedException ex) throws IOException {
        // 由于使用了shiro作为权限组件, 所以这里的HttpServletRequest会被shiro代理, 实参是ShiroHttpServletRequest类型, 通过request取当前用户的时候需要注意下
        // 这里选择直接从shiro中获取
        // User currentUser = (User) ((HttpServletRequest) ((ShiroHttpServletRequest) request).getRequest()).getSession().getAttribute(Constants.CURRENT_USER);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User currentUser = (User) session.getAttribute(Constants.CURRENT_USER);
        String username = "unknown";
        if (currentUser != null) {
            username = currentUser.getUsername();
        }
        UserLogUtil.log(username, "非法访问", "未被授权");
        String ajax = request.getHeader("x-requested-with");
        if (StringUtil.isNotEmpty(ajax) && ajax.equals("XMLHttpRequest")) {
            Result result = new Result(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
            writeJSON(response, result);
        } else {
            response.sendRedirect(request.getContextPath() + "/unauthorized");
        }
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void resolveException(HttpServletRequest request, HttpServletResponse response, RuntimeException ex) throws IOException {
        logger.error("系统异常", ex);
        Result result = new Result(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        writeJSON(response, result);
    }

    protected void writeJSON(HttpServletResponse response, String json) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void writeJSON(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue));
    }
}
