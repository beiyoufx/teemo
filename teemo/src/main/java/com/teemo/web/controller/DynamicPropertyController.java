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
import com.teemo.entity.DynamicProperty;
import com.teemo.entity.User;
import com.teemo.service.DynamicPropertyService;
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
 * @date 17-1-6 上午11:09
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.web.controller
 */
@Controller
@RequestMapping("/sys/property")
public class DynamicPropertyController extends BaseController {
    @Resource
    private DynamicPropertyService dynamicPropertyService;

    @RequiresPermissions(value = "sys:dynamicProperty:view")
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletResponse response) throws IOException {
        return "admin/property/main";
    }

    @RequiresPermissions(value = "sys:dynamicProperty:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void get(HttpServletResponse response,
                    @PathVariable Long id) throws IOException {
        DynamicProperty dynamicProperty = dynamicPropertyService.get(id);
        writeJSON(response, dynamicProperty);
    }

    @RequiresPermissions(value = "sys:dynamicProperty:view")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public void find(HttpServletResponse response, BTQueryParameter queryParameter) throws IOException {
        Page<DynamicProperty> page = new Page<DynamicProperty>();
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
                Condition condition = Condition.newCondition(SearchType.OR, "key", SearchOperator.like, queryParameter.getSearchText());
                condition.addChildCondition(Condition.newCondition(SearchType.OR, "value", SearchOperator.like, queryParameter.getSearchText()));
                searchable.addSearchFilter(condition);
            }
            searchable.addSearchFilter(Condition.newCondition(SearchType.AND ,"deleted", SearchOperator.eq, Boolean.FALSE));

            List<DynamicProperty> dynamicProperties = dynamicPropertyService.find(searchable);
            Long total = dynamicPropertyService.count(searchable);
            page.setTotal(total);
            page.setPageSize(queryParameter.getPageSize());
            page.setRecords(dynamicProperties);
        }
        writeJSON(response, page);
    }

    @RequiresPermissions(value = "sys:dynamicProperty:create")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() throws IOException {
        return "admin/property/edit";
    }

    @RequiresPermissions(value = "sys:dynamicProperty:update")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletResponse response, @PathVariable Long id) throws IOException {
        ModelAndView mav = new ModelAndView();
        DynamicProperty dynamicProperty = dynamicPropertyService.get(id);
        mav.addObject("dynamicProperty", dynamicProperty);
        mav.setViewName("admin/property/edit");
        return mav;
    }

    @RequiresPermissions(value = "sys:dynamicProperty:update")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(HttpServletResponse response, DynamicProperty dynamicProperty) throws IOException {
        User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        if (dynamicProperty.getId() == null) {
            dynamicProperty.setAuthor(currentUser.getUsername());
            dynamicPropertyService.persist(dynamicProperty);
        } else {
            dynamicPropertyService.updateVersion(dynamicProperty);
        }
        UserLogUtil.log(currentUser.getUsername(), "保存动态属性信息成功", "被操作动态属性Key:{}", dynamicProperty.getDynamicPropertyKey());
        Result result = new Result(1, "保存动态属性信息成功.");
        writeJSON(response, result);
    }

    @RequiresPermissions(value = "sys:dynamicProperty:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void delete(HttpServletResponse response, @PathVariable Long id) throws IOException {
        boolean success = dynamicPropertyService.logicDelete(id);
        Result result;
        if (success) {
            User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
            UserLogUtil.log(user.getUsername(), "删除动态属性成功", "被操作ID:{}", id);
            result = new Result(1, "删除动态属性成功");
        } else {
            result = new Result(-1, "删除动态属性失败");
        }
        writeJSON(response, result);
    }
}
