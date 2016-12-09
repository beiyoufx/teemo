/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.dao;

import com.teemo.entity.User;
import core.dao.BaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @author yongjie.teng
 * @version 1.0
 * @date 16-10-27
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.dao
 * @project teemo
 */
@Repository
public class UserDao extends BaseDao<User> {

    /**
     * 根据用户主键删除用户角色关系
     * @param id 用户ID
     */
    public void deleteUserRoleById(Long id) {
        Query query = this.getSession().createSQLQuery("delete from user_role where user_id = :userId");
        query.setParameter("userId", id);
        query.executeUpdate();
    }
}
