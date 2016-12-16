/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.dao;

import com.teemo.entity.User;
import core.dao.BaseDao;
import core.util.StringUtil;
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
        Query query = getSession().createSQLQuery("delete from user_role where user_id = :userId");
        query.setParameter("userId", id);
        query.executeUpdate();
    }

    /**
     * 部分更新用户表字段
     * 只更新以下字段：nickname/email/mobilePhone/departmentKey/status/modifyTime
     * @param user 待更新实体
     */
    public void mergeUser(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("update " + User.class.getName() + " e set ");

        String[] props = new String[5];
        Object[] values = new Object[5];

        int pos = 0;
        // 拼接需要更新的字段属性名
        if (StringUtil.isNotEmpty(user.getNickname())) {
            props[pos] = "nickname";
            values[pos] = user.getNickname();
            sb.append(props[pos] + " = :v_" + props[pos] + ",");
            pos++;
        }
        if (StringUtil.isNotEmpty(user.getEmail())) {
            props[pos] = "email";
            values[pos] = user.getEmail();
            sb.append(props[pos] + " = :v_" + props[pos] + ",");
            pos++;
        }
        if (StringUtil.isNotEmpty(user.getDepartmentKey())) {
            props[pos] = "departmentKey";
            values[pos] = user.getDepartmentKey();
            sb.append(props[pos] + " = :v_" + props[pos] + ",");
            pos++;
        }
        if (StringUtil.isNotEmpty(user.getMobilePhone())) {
            props[pos] = "mobilePhone";
            values[pos] = user.getMobilePhone();
            sb.append(props[pos] + " = :v_" + props[pos] + ",");
            pos++;
        }
        if (user.getStatus() != null) {
            props[pos] = "status";
            values[pos] = user.getStatus();
            sb.append(props[pos] + " = :v_" + props[pos] + ",");
        }

        // 拼接固定语句
        sb.append("modifyTime = now() where id = " + user.getId());

        Query query = getSession().createQuery(sb.toString());
        // 拼接需要更新的字段属性值
        for (int i = 0; i < values.length; i++) {
            query.setParameter("v_" + props[i], values[i]);
        }
        query.executeUpdate();
    }
}
