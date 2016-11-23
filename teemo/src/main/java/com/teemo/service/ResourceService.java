/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.service;

import com.teemo.dao.ResourceDao;
import com.teemo.entity.Resource;
import core.service.BaseService;
import core.util.StringUtil;
import org.springframework.stereotype.Service;

/**
 * @author yongjie.teng
 * @date 16-11-22 上午9:31
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.service
 */
@Service
public class ResourceService extends BaseService<Resource> {
    private ResourceDao resourceDao;
    @javax.annotation.Resource
    public void setResourceDao(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
        this.dao = resourceDao;
    }

    /**
     * 得到真实的资源标识  即 上级资源标识:子级资源标识
     * @param resource 目标资源
     * @return String
     */
    public String findActualResourceKey(Resource resource) {

        if(resource == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder(resource.getResourceKey());

        boolean hasKey = !StringUtil.isEmpty(resource.getResourceKey());

        Resource parent = get(resource.getParentId());
        while(parent != null) {
            if(!StringUtil.isEmpty(parent.getResourceKey())) {
                sb.insert(0, parent.getResourceKey() + ":");
                hasKey = true;
            }
            parent = get(parent.getParentId());
        }

        // 如果用户没有声明资源标识，并且没有上级资源标识，那么就为空
        if(!hasKey) {
            return "";
        }

        // 删除末尾的:
        int length = sb.length();
        if(length > 0 && sb.lastIndexOf(":") == length - 1) {
            sb.deleteCharAt(length - 1);
        }

        // 如果有子级资源，最后拼一个*
        boolean hasChildren = false;
        for(Resource r : findAll()) {
            if(resource.getId().equals(r.getParentId())) {
                hasChildren = true;
                break;
            }
        }
        if(hasChildren) {
            sb.append(":*");
        }

        return sb.toString();
    }
}
