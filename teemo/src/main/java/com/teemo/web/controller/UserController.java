/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.web.controller;

import com.teemo.core.Constants;
import com.teemo.entity.User;
import com.teemo.service.UserService;
import core.support.Condition;
import core.support.PageRequest;
import core.support.SearchRequest;
import core.support.Sort;
import core.support.search.SearchOperator;
import core.support.search.SearchType;
import core.support.search.Searchable;
import core.web.controller.BaseController;
import com.teemo.core.util.UserLogUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yongjie.teng
 * @date 16-10-27
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.web.controller
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;

    @RequiresPermissions(value = "sys:user:view")
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletResponse response) throws IOException {
        return "admin/user/main";
    }

    @RequiresPermissions(value = "sys:user:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void get(HttpServletResponse response,
                     @PathVariable Long id) throws IOException {
        User user = userService.get(id);
        UserLogUtil.log(user.getUsername(), "获取用户信息成功", "User ID=" + user.getId());
        writeJSON(response, user);
    }

    @RequiresPermissions(value = "sys:user:view")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public void find(HttpServletResponse response) throws IOException {
        Searchable searchable = SearchRequest.newSearchRequest(new Sort(Sort.Direction.desc, "id"), PageRequest.newPageRequest(0, 10));
        searchable.addSearchParam("username", "马云");
        Condition condition = Condition.newCondition(SearchType.OR, "username", SearchOperator.eq, "马化腾");
        condition.addChildCondition("email", SearchOperator.eq, "123");
        searchable.addSearchFilter(condition);
        writeJSON(response, userService.find(searchable));
    }

    @RequiresPermissions(value = "sys:user:update")
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public void update(HttpServletResponse response) throws IOException {
        Searchable searchable = SearchRequest.newSearchRequest();
        searchable.addSearchParam("username", "马化腾");
        searchable.addSearchParam("password", "123");
        userService.update(searchable, "email", "tencent@qq.com");
        writeJSON(response, "更新成功");
    }
}
