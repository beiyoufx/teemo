/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.service;

import com.teemo.dao.RoleDao;
import com.teemo.entity.Role;
import com.teemo.entity.RoleResourcePermission;
import core.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author yongjie.teng
 * @date 16-11-22 上午9:31
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.service
 */
@Service
public class RoleService extends BaseService<Role> {
    @Resource
    private RoleResourcePermissionService roleResourcePermissionService;
    private RoleDao roleDao;
    @Resource
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
        this.dao = roleDao;
    }

    /**
     * 更新角色信息和权限
     * @param rrps 角色权限信息
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void authRole(Set<RoleResourcePermission> rrps) {
        if (rrps != null && !rrps.isEmpty()) {
            // 删除角色原有权限信息
            Long roleId = ((RoleResourcePermission)rrps.toArray()[0]).getRole().getId();
            roleResourcePermissionService.deleteByRoleId(roleId);
            // 更新该角色所对应的权限信息
            for (RoleResourcePermission rrp : rrps) {
                roleResourcePermissionService.persist(rrp);
            }
        }
    }

    /**
     * 删除角色信息和权限
     * @param role 角色
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void deleteRole(Role role) {
        if (role != null) {
            if (role.getResourcePermissions() != null && !role.getResourcePermissions().isEmpty()) {
                // 删除该角色所对应的权限信息
                for (RoleResourcePermission rrp : role.getResourcePermissions()) {
                    roleResourcePermissionService.delete(rrp);
                }
            }
            deleteUserRole(role);
            delete(role);
        }
    }

    /**
     * 根据角色删除用户角色关系
     * @param role 角色
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void deleteUserRole(Role role) {
        if (role != null) {
            roleDao.deleteUserRoleById(role.getId());
        }
    }
}
