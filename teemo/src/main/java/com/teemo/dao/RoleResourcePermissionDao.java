/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.dao;

import com.teemo.entity.RoleResourcePermission;
import core.dao.BaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @author yongjie.teng
 * @date 16-12-9 下午6:13
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.dao
 */
@Repository
public class RoleResourcePermissionDao extends BaseDao<RoleResourcePermission> {

    /**
     * 根据角色主键删除角色权限信息
     * @param roleId 角色ID
     */
    public void deleteByRoleId(Long roleId) {
        Query query = this.getSession().createSQLQuery("delete from role_resource_permission where role_id = :roleId");
        query.setParameter("roleId", roleId);
        query.executeUpdate();
    }
}
