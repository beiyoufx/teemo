/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.dao;

import com.teemo.entity.Role;
import core.dao.BaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @author yongjie.teng
 * @date 16-11-22 上午9:39
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.dao
 */
@Repository
public class RoleDao extends BaseDao<Role> {

    /**
     * 根据角色主键删除用户角色关系
     * @param id 角色ID
     */
    public void deleteUserRoleById(Long id) {
        Query query = this.getSession().createSQLQuery("delete from user_role where role_id = :roleId");
        query.setParameter("roleId", id);
        query.executeUpdate();
    }
}
