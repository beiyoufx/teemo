/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.web.controller;

import com.alibaba.fastjson.JSON;
import com.teemo.core.Constants;
import com.teemo.core.util.UserLogUtil;
import com.teemo.dto.BTQueryParameter;
import com.teemo.dto.Page;
import com.teemo.dto.Result;
import com.teemo.entity.Role;
import com.teemo.entity.User;
import com.teemo.service.RoleService;
import core.support.Condition;
import core.support.PageRequest;
import core.support.SearchRequest;
import core.support.Sort;
import core.support.search.SearchOperator;
import core.support.search.SearchType;
import core.support.search.Searchable;
import core.util.StringUtil;
import core.web.controller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author yongjie.teng
 * @date 16-12-8 下午7:20
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.web.controller
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController{
    @Resource
    private RoleService roleService;

    @RequiresPermissions(value = "sys:role:view")
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletResponse response) throws IOException {
        return "admin/role/main";
    }

    @RequiresPermissions(value = "sys:role:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void get(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Role role = roleService.get(id);
        writeJSON(response, role);
    }

    @RequiresPermissions(value = "sys:role:view")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public void find(HttpServletResponse response, BTQueryParameter queryParameter) throws IOException {
        Page<Role> page = new Page<Role>();
        if (queryParameter != null) {
            // 设置排序
            Sort sort = new Sort(Sort.Direction.desc, "id");
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
                searchable.addSearchFilter(Condition.newCondition(SearchType.OR, "roleKey", SearchOperator.like, queryParameter.getSearchText()));
                searchable.addSearchFilter(Condition.newCondition(SearchType.OR, "roleValue", SearchOperator.like, queryParameter.getSearchText()));
            }

            List<Role> roles = roleService.find(searchable);
            Long total = roleService.count(searchable);
            page.setTotal(total);
            page.setPageSize(queryParameter.getPageSize());
            page.setRecords(roles);
        }
        writeJSON(response, page);
    }

    @RequiresPermissions(value = "sys:role:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(HttpServletResponse response, @RequestBody String body) throws IOException {
        Role role = JSON.parseObject(body, Role.class);
        roleService.updateRole(role);
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        UserLogUtil.log(user.getUsername(), "更新角色信息成功", "被操作ID:{}", role.getId());
        Result result = new Result(1, "更新角色信息成功.");
        writeJSON(response, result);
    }

    @RequiresPermissions(value = "sys:role:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void delete(HttpServletResponse response, @PathVariable Long id) throws IOException {
        Role role = roleService.get(id);
        roleService.deleteRole(role);
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        UserLogUtil.log(user.getUsername(), "删除角色成功", "被操作ID:{}", id);
        Result result = new Result(1, "删除角色成功");
        writeJSON(response, result);
    }

    @RequiresPermissions(value = "sys:role:view")
    @RequestMapping(value = "/auth/{id}", method = RequestMethod.GET)
    public ModelAndView auth(@PathVariable Long id, HttpServletResponse response) throws IOException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/role/auth");
        if (id != null) {
            Role role = roleService.get(id);
            mav.addObject("role", role);
        }
        return mav;
    }

}
