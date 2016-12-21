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
import com.teemo.entity.ResourceNode;
import com.teemo.entity.Role;
import com.teemo.entity.RoleResourcePermission;
import com.teemo.entity.User;
import com.teemo.service.ResourceService;
import com.teemo.service.RoleService;
import com.teemo.service.UserService;
import core.web.controller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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
    @Resource
    private ResourceService resourceService;

    @RequiresPermissions(value = "sys:auth:view")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public ModelAndView roleAuth(HttpServletResponse response, @PathVariable Long id) throws IOException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/role/auth");
        if (id != null) {
            Role role = roleService.get(id);
            List<ResourceNode> resourceNodes = resourceService.findResourceNode(role);
            mav.addObject("resourceNodes", resourceNodes);
            mav.addObject("role", role);
        }
        return mav;
    }

    @RequiresPermissions(value = "sys:auth:update")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.POST)
    public void authRole(HttpServletResponse response, @PathVariable Long id, @RequestBody String body) throws IOException {
        Result result;
        HashSet<RoleResourcePermission> rrps = JSON.parseObject(body, new TypeReference<HashSet<RoleResourcePermission>>() {});
        Role role = roleService.get(id);
        if (role == null) {
            result = new Result(-1, "角色不存在");
        } else {
            roleService.auth(role, rrps);
            User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
            UserLogUtil.log(user.getUsername(), "角色授权成功", "被授权角色:{}", JSON.toJSONString(rrps));
            result = new Result(1, "角色授权成功");
        }
        writeJSON(response, result);
    }

    @RequiresPermissions(value = "sys:auth:view")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ModelAndView userAuth(HttpServletResponse response, @PathVariable Long id) throws IOException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/user/auth");
        if (id != null) {
            User user = userService.get(id);
            List<Role> roles = roleService.findAll();
            mav.addObject("roles", roles);
            mav.addObject("user", user);
        }
        return mav;
    }

    @RequiresPermissions(value = "sys:auth:update")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
    public void authUser(HttpServletResponse response, @PathVariable Long id, Long[] roleIds) throws IOException {
        Result result;
        User user = userService.get(id);
        Set<Role> roles = new HashSet<Role>();
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                Role role = roleService.get(roleId);
                if (role != null) {
                    roles.add(role);
                }
            }
        }
        if (user == null) {
            result = new Result(-1, "用户不存在");
        } else {
            userService.auth(user, roles);
            User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
            UserLogUtil.log(currentUser.getUsername(), "用户授权成功", "被授权用户和角色:{}", user.getUsername() + "=>" + JSON.toJSONString(roles));
            result = new Result(1, "用户授权成功");
        }
        writeJSON(response, result);
    }
}
