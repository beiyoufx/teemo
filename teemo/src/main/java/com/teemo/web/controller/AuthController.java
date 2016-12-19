/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.teemo.core.Constants;
import com.teemo.core.util.UserLogUtil;
import com.teemo.dto.Result;
import com.teemo.entity.Role;
import com.teemo.entity.RoleResourcePermission;
import com.teemo.entity.User;
import com.teemo.service.RoleService;
import com.teemo.service.UserService;
import core.web.controller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yongjie.teng
 * @date 16-12-18 下午6:46
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.web.controller
 */
@Controller
@RequestMapping("/sys/auth")
public class AuthController extends BaseController {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    @RequiresPermissions(value = "sys:auth:update")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void auth(HttpServletResponse response, Long userId, @RequestParam("roleIds[]") Long[] roleIds) throws IOException {
        Result result = new Result(0, "用户授权失败");
        if (userId != null) {
            User user = userService.get(userId);
            Set<Role> roles = new HashSet<Role>();
            for (Long roleId : roleIds) {
                Role role = roleService.get(roleId);
                if (role != null) {
                    roles.add(role);
                }
            }
            if (user != null && !roles.isEmpty()) {
                userService.auth(user, roles);
                User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
                UserLogUtil.log(currentUser.getUsername(), "用户授权成功", "被授权用户和角色:{}", userId + "=>" + JSON.toJSONString(roles));
                result = new Result(1, "用户授权成功");
            }
        }
        writeJSON(response, result);
    }
}
