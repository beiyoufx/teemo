/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.web.controller;

import com.teemo.dto.Result;
import core.web.controller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yongjie.teng
 * @date 16-11-22 上午8:59
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.web.controller
 */
@Controller
public class LoginController extends BaseController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String goLogin() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletRequest request,
                      HttpServletResponse response,
                      String username,
                      String password,
                      Boolean rememberMe) throws IOException {
        Result result;
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
            subject.login(token);
            result = new Result(1, "登陆成功");
        } catch (UnknownAccountException ua) {
            result = new Result(-1, "用户名错误");
        } catch (IncorrectCredentialsException ic) {
            result = new Result(-2, "密码错误");
        } catch (LockedAccountException la) {
            result = new Result(-3, "账号被锁定");
        } catch (Exception e) {
            result = new Result(-4, "未知错误");
        }
        writeJSON(response, result);
    }

    @RequestMapping("/goUnauthorized")
    public String goUnauthorized() {
        return "redirect:/unauthorized";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized() {
        return "error/unauthorized";
    }
}
