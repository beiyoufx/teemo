/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.web.controller;

import com.teemo.core.Constants;
import com.teemo.core.util.UserLogUtil;
import com.teemo.dto.BTQueryParameter;
import com.teemo.dto.Page;
import com.teemo.dto.Result;
import com.teemo.entity.Department;
import com.teemo.entity.User;
import com.teemo.service.DepartmentService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author yongjie.teng
 * @date 16-12-18 上午10:39
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.web.controller
 */
@Controller
@RequestMapping("/sys/department")
public class DepartmentController extends BaseController {
    @Resource
    private DepartmentService departmentService;

    @RequiresPermissions(value = "sys:department:view")
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletResponse response) throws IOException {
        return "admin/department/main";
    }

    @RequiresPermissions(value = "sys:department:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void get(HttpServletResponse response,
                    @PathVariable Long id) throws IOException {
        Department department = departmentService.get(id);
        writeJSON(response, department);
    }

    @RequiresPermissions(value = "sys:department:view")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public void find(HttpServletResponse response, BTQueryParameter queryParameter) throws IOException {
        Page<Department> page = new Page<Department>();
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
                Condition condition = Condition.newCondition(SearchType.OR, "departmentKey", SearchOperator.like, queryParameter.getSearchText());
                condition.addChildCondition(Condition.newCondition(SearchType.OR, "departmentValue", SearchOperator.like, queryParameter.getSearchText()));
                searchable.addSearchFilter(condition);
            }
            searchable.addSearchFilter(Condition.newCondition(SearchType.AND ,"deleted", SearchOperator.eq, Boolean.FALSE));

            List<Department> departments = departmentService.find(searchable);
            Long total = departmentService.count(searchable);
            page.setTotal(total);
            page.setPageSize(queryParameter.getPageSize());
            page.setRecords(departments);
        }
        writeJSON(response, page);
    }

    @RequiresPermissions(value = "sys:department:create")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() throws IOException {
        ModelAndView mav = new ModelAndView();
        List<Department> departments = departmentService.findAll();
        mav.setViewName("admin/department/edit");
        return mav;
    }

    @RequiresPermissions(value = "sys:department:update")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletResponse response, @PathVariable Long id) throws IOException {
        ModelAndView mav = new ModelAndView();
        Department department = departmentService.get(id);
        List<Department> departments = departmentService.findAll();
        mav.addObject("department", department);
        mav.addObject("departments", departments);
        mav.setViewName("admin/department/edit");
        return mav;
    }

    @RequiresPermissions(value = "sys:department:update")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(HttpServletResponse response, Department department) throws IOException {
        if (department.getId() == null) {
            departmentService.persist(department);
        } else {
            departmentService.merge(department);
        }
        User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        UserLogUtil.log(currentUser.getUsername(), "保存部门信息成功", "被操作部门Key:{}", department.getDepartmentKey());
        Result result = new Result(1, "保存部门信息成功.");
        writeJSON(response, result);
    }

    @RequiresPermissions(value = "sys:department:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void delete(HttpServletResponse response, @PathVariable Long id) throws IOException {
        boolean success = departmentService.logicDelete(id);
        Result result;
        if (success) {
            User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
            UserLogUtil.log(user.getUsername(), "删除部门成功", "被操作ID:{}", id);
            result = new Result(1, "删除部门成功");
        } else {
            result = new Result(-1, "删除部门失败");
        }
        writeJSON(response, result);
    }
}
