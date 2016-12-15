/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.service;

import com.teemo.entity.*;
import core.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yongjie.teng
 * @date 16-11-22 下午3:55
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.service
 */
@Service
public class AuthorizationService {
    @javax.annotation.Resource
    private ResourceService resourceService;
    @javax.annotation.Resource
    private PermissionService permissionService;

    /**
     * 获取目标用户的角色字符串集合
     * @param user 目标用户
     * @return Set<String>
     */
    public Set<String> findRolesStr(User user) {
        Set<String> roles = new HashSet<String>();
        for (Role role : user.getRoles()) {
            roles.add(role.getRoleKey());
        }
        return roles;
    }

    /**
     * 获取目标用户的权限字符串集合
     * @param user 目标用户
     * @return Set<String>
     */
    public Set<String> findPermissionsStr(User user) {
        Set<String> permissions = new HashSet<String>();
        for (Role role : user.getRoles()) {
            permissions.addAll(findPermissionsStr(role));
        }
        return permissions;
    }

    /**
     * 获取目标角色的权限字符串集合
     * @param role 目标角色
     * @return Set<String>
     */
    public Set<String> findPermissionsStr(Role role) {
        Set<String> permissions = new HashSet<String>();
        Resource resource = null;
        for (RoleResourcePermission rrp : role.getResourcePermissions()) {
            resource = resourceService.get(rrp.getResourceId());

            // 只有在资源可用的情况下才进行资源字符串查询
            if (resource != null && Boolean.TRUE.equals(resource.getAvailable())) {
                String actualResourceKey = resourceService.findActualResourceKey(resource);

                // 只有在资源字符串存在的情况下才进行权限字符串查询
                if (StringUtil.isNotEmpty(actualResourceKey)) {
                    for (Long permissionId : rrp.getPermissionIds()) {
                        Permission permission = permissionService.get(permissionId);

                        // 只有在权限可用的情况下才进行权限字符串拼接
                        if (permission != null && Boolean.TRUE.equals(permission.getAvailable())) {

                            // 权限使用逐条声明的方式，如[user:view][user:delete]而不是[user:view,delete]
                            permissions.add(actualResourceKey + ":" + permission.getPermissionKey());
                        }
                    }
                }
            }
        }
        return permissions;
    }
}
