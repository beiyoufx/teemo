package com.teemo.web.controller;

import com.teemo.core.Constants;
import com.teemo.core.util.UserLogUtil;
import com.teemo.dto.BTQueryParameter;
import com.teemo.dto.Page;
import com.teemo.dto.Result;
import com.teemo.entity.Permission;
import com.teemo.entity.User;
import com.teemo.service.PermissionService;
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
 * @date 16-12-16 下午6:28
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.web.controller
 */
@Controller
@RequestMapping("/sys/permission")
public class PermissionController extends BaseController {
    @Resource
    private PermissionService permissionService;

    @RequiresPermissions(value = "sys:permission:view")
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletResponse response) throws IOException {
        return "admin/permission/main";
    }

    @RequiresPermissions(value = "sys:permission:view")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public void find(HttpServletResponse response, BTQueryParameter queryParameter) throws IOException {
        Page<Permission> page = new Page<Permission>();
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
                searchable.addSearchFilter(Condition.newCondition(SearchType.OR, "permissionKey", SearchOperator.like, queryParameter.getSearchText()));
                searchable.addSearchFilter(Condition.newCondition(SearchType.OR, "permissionValue", SearchOperator.like, queryParameter.getSearchText()));
            }

            List<Permission> permissions = permissionService.find(searchable);
            Long total = permissionService.count(searchable);
            page.setTotal(total);
            page.setPageSize(queryParameter.getPageSize());
            page.setRecords(permissions);
        }
        writeJSON(response, page);
    }

    @RequiresPermissions(value = "sys:permission:create")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() throws IOException {
        return "admin/permission/edit";
    }

    @RequiresPermissions(value = "sys:permission:update")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletResponse response, @PathVariable Long id) throws IOException {
        ModelAndView mav = new ModelAndView();
        Permission permission = permissionService.get(id);
        mav.addObject("permission", permission);
        mav.setViewName("admin/permission/edit");
        return mav;
    }

    @RequiresPermissions(value = "sys:permission:update")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(HttpServletResponse response, Permission permission) throws IOException {
        if (permission.getId() == null) {
            permissionService.persist(permission);
        } else {
            permissionService.merge(permission);
        }
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        UserLogUtil.log(user.getUsername(), "保存权限信息成功", "被操作权限Key:{}", permission.getPermissionKey());
        Result result = new Result(1, "保存权限信息成功.");
        writeJSON(response, result);
    }

    @RequiresPermissions(value = "sys:permission:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void delete(HttpServletResponse response, @PathVariable Long id) throws IOException {
        Permission permission = permissionService.get(id);
        permissionService.delete(permission);
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        UserLogUtil.log(user.getUsername(), "删除权限成功", "被操作权限Key:{}", permission.getPermissionKey());
        Result result = new Result(1, "删除权限成功");
        writeJSON(response, result);
    }
}
