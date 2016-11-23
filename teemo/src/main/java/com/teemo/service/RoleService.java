/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.service;

import com.teemo.dao.RoleDao;
import com.teemo.entity.Role;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yongjie.teng
 * @date 16-11-22 上午9:31
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.service
 */
@Service
public class RoleService extends BaseService<Role> {
    private RoleDao roleDao;
    @Resource
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
        this.dao = roleDao;
    }
}
