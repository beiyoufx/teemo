/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.service;

import com.teemo.dao.PermissionDao;
import com.teemo.entity.Permission;
import core.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * @author yongjie.teng
 * @date 16-11-22 上午9:31
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.service
 */
@Service
public class PermissionService extends BaseService<Permission> {
    private PermissionDao permissionDao;
    @javax.annotation.Resource
    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
        this.dao = permissionDao;
    }
}
