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
import com.teemo.entity.Resource;
import com.teemo.entity.ResourceType;
import com.teemo.entity.User;
import com.teemo.service.ResourceService;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author yongjie.teng
 * @date 16-12-14 下午3:57
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.web.controller
 */
@Controller
@RequestMapping("/sys/resource")
public class ResourceController extends BaseController {
    @javax.annotation.Resource
    private ResourceService resourceService;

    @RequiresPermissions(value = "sys:resource:view")
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletResponse response) throws IOException {
        return "admin/resource/main";
    }

    @RequiresPermissions(value = "sys:resource:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void get(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Resource resource = resourceService.get(id);
        writeJSON(response, resource);
    }

    @RequiresPermissions(value = "sys:resource:view")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public void find(HttpServletResponse response, BTQueryParameter queryParameter) throws IOException {
        Page<Resource> page = new Page<Resource>();
        if (queryParameter != null) {

            // 设置分页
            PageRequest pageRequest = PageRequest.newPageRequest(0, 10);
            if (queryParameter.getPageNumber() != null && queryParameter.getPageNumber() >= 0
                    && queryParameter.getPageSize() != null && queryParameter.getPageSize() > 0) {
                pageRequest = PageRequest.newPageRequest(queryParameter.getPageNumber(), queryParameter.getPageSize());
            } else {
                queryParameter.setPageSize(pageRequest.getPageSize());
            }

            // 设置查询条件
            Searchable searchable = SearchRequest.newSearchRequest();
            searchable.setPage(pageRequest);
            if (StringUtil.isNotEmpty(queryParameter.getSearchText())) {
                searchable.addSearchFilter(Condition.newCondition(SearchType.OR, "resourceKey", SearchOperator.like, queryParameter.getSearchText()));
                searchable.addSearchFilter(Condition.newCondition(SearchType.OR, "resourceValue", SearchOperator.like, queryParameter.getSearchText()));
            }

            List<Resource> resources = resourceService.find(searchable);
            Long total = resourceService.count(searchable);
            page.setTotal(total);
            page.setPageSize(queryParameter.getPageSize());
            page.setRecords(resources);
        }
        writeJSON(response, page);
    }

    @RequiresPermissions(value = "sys:resource:create")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() throws IOException {
        ModelAndView mav = new ModelAndView();
        List<Resource> allResources = resourceService.findAll();
        mav.addObject("resourceTypes", ResourceType.values());
        mav.addObject("allResources", allResources);
        mav.setViewName("admin/resource/edit");
        return mav;
    }

    @RequiresPermissions(value = "sys:resource:update")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletResponse response, @PathVariable Long id) throws IOException {
        ModelAndView mav = new ModelAndView();
        Resource resource = resourceService.get(id);
        List<Resource> allResources = resourceService.findAll();
        mav.addObject("resource", resource);
        mav.addObject("resourceTypes", ResourceType.values());
        mav.addObject("allResources", allResources);
        mav.setViewName("admin/resource/edit");
        return mav;
    }

    @RequiresPermissions(value = "sys:resource:update")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(HttpServletResponse response, Resource resource) throws IOException {
        if (resource.getId() == null) {
            resourceService.persist(resource);
        } else {
            resourceService.merge(resource);
        }
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        UserLogUtil.log(user.getUsername(), "保存资源信息成功", "被操作ID:{}", resource.getId());
        Result result = new Result(1, "保存资源信息成功.");
        writeJSON(response, result);
    }

    @RequiresPermissions(value = "sys:resource:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void delete(HttpServletResponse response, @PathVariable Long id) throws IOException {
        Resource resource = resourceService.get(id);
        resourceService.deleteResource(resource);
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        UserLogUtil.log(user.getUsername(), "删除资源成功", "被操作资源Value:{}", resource.getResourceValue());
        Result result = new Result(1, "删除资源成功");
        writeJSON(response, result);
    }
}
