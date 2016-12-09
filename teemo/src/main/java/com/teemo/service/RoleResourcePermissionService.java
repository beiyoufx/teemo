/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.service;

import com.teemo.dao.RoleResourcePermissionDao;
import com.teemo.entity.RoleResourcePermission;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yongjie.teng
 * @date 16-12-9 下午6:12
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.service
 */
@Service
public class RoleResourcePermissionService extends BaseService<RoleResourcePermission> {
    private RoleResourcePermissionDao roleResourcePermissionDao;
    @Resource
    public void setRoleResourcePermissionDao(RoleResourcePermissionDao roleResourcePermissionDao) {
        this.roleResourcePermissionDao = roleResourcePermissionDao;
        this.dao = roleResourcePermissionDao;
    }
}
