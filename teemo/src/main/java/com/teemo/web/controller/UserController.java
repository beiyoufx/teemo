/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.web.controller;

import com.teemo.core.Constants;
import com.teemo.dto.BTQueryParameter;
import com.teemo.dto.Page;
import com.teemo.dto.Result;
import com.teemo.entity.Department;
import com.teemo.entity.Role;
import com.teemo.entity.User;
import com.teemo.service.DepartmentService;
import com.teemo.service.RoleService;
import com.teemo.service.UserService;
import core.support.Condition;
import core.support.PageRequest;
import core.support.SearchRequest;
import core.support.Sort;
import core.support.search.SearchOperator;
import core.support.search.SearchType;
import core.support.search.Searchable;
import core.util.StringUtil;
import core.web.controller.BaseController;
import com.teemo.core.util.UserLogUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
    @Resource
    private DepartmentService departmentService;
    @Resource
    private RoleService roleService;

    @RequiresPermissions(value = "sys:user:view")
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletResponse response) throws IOException {
        return "admin/user/main";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(HttpServletResponse response) throws IOException {
        return "admin/user/profile";
    }

    @RequiresPermissions(value = "sys:user:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void get(HttpServletResponse response,
                     @PathVariable Long id) throws IOException {
        User user = userService.get(id);
        writeJSON(response, user);
    }

    @RequiresPermissions(value = "sys:user:view")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public void find(HttpServletResponse response, BTQueryParameter queryParameter) throws IOException {
        Page<User> page = new Page<User>();
        if (queryParameter != null) {
            // 设置排序
            Sort sort = new Sort(Sort.Direction.desc, "modifyTime");
            if (StringUtil.isNotEmpty(queryParameter.getSortName())) {
                if (Sort.Direction.asc.name().equalsIgnoreCase(queryParameter.getSortOrder())) {
                    sort = new Sort(Sort.Direction.asc, queryParameter.getSortName());
                }
            }

            // 设置分页
            PageRequest pageRequest = PageRequest.newPageRequest(0, 10);
            if (queryParameter.getPageNumber() != null && queryParameter.getPageNumber() >= 0
                    && queryParameter.getPageSize() != null && queryParameter.getPageSize() > 0) {
                pageRequest = PageRequest.newPageRequest(queryParameter.getPageNumber(), queryParameter.getPageSize());
            } else {
                queryParameter.setPageSize(pageRequest.getPageSize());
            }

            // 设置查询条件
            Searchable searchable = SearchRequest.newSearchRequest(sort, pageRequest);
            if (StringUtil.isNotEmpty(queryParameter.getSearchText())) {
                Condition condition = Condition.newCondition(SearchType.OR, "username", SearchOperator.like, queryParameter.getSearchText());
                condition.addChildCondition(Condition.newCondition(SearchType.OR, "nickname", SearchOperator.like, queryParameter.getSearchText()));
                condition.addChildCondition(Condition.newCondition(SearchType.OR, "email", SearchOperator.like, queryParameter.getSearchText()));
                condition.addChildCondition(Condition.newCondition(SearchType.OR, "mobilePhone", SearchOperator.like, queryParameter.getSearchText()));
                searchable.addSearchFilter(condition);
            }
            searchable.addSearchFilter(Condition.newCondition(SearchType.AND ,"deleted", SearchOperator.eq, Boolean.FALSE));

            List<User> users = userService.find(searchable);
            Long total = userService.count(searchable);
            page.setTotal(total);
            page.setPageSize(queryParameter.getPageSize());
            page.setRecords(users);
        }
        writeJSON(response, page);
    }

    @RequiresPermissions(value = "sys:user:update")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletResponse response, @PathVariable Long id) throws IOException {
        ModelAndView mav = new ModelAndView();
        User user = userService.get(id);
        List<Department> departments = departmentService.findAll();
        mav.addObject("user", user);
        mav.addObject("departments", departments);
        mav.setViewName("admin/user/edit");
        return mav;
    }

    @RequiresPermissions(value = "sys:user:update")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(HttpServletResponse response, User user) throws IOException {
        if (user.getId() == null) {
            userService.persist(user);
        } else {
            userService.mergeUser(user);
        }
        User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        UserLogUtil.log(currentUser.getUsername(), "保存用户信息成功", "被操作用户名:{}", user.getUsername());
        Result result = new Result(1, "保存用户信息成功.");
        writeJSON(response, result);
    }

    @RequiresPermissions(value = "sys:user:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void delete(HttpServletResponse response, @PathVariable Long id) throws IOException {
        boolean success = userService.logicDelete(id);
        Result result;
        if (success) {
            User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
            UserLogUtil.log(user.getUsername(), "删除用户成功", "被操作ID:{}", id);
            result = new Result(1, "删除用户成功");
        } else {
            result = new Result(-1, "删除用户失败");
        }
        writeJSON(response, result);
    }

}
