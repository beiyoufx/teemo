package com.teemo.dao;

import com.teemo.entity.Resource;
import com.teemo.entity.RoleResourcePermission;
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
public class ResourceDao extends BaseDao<Resource> {

    /**
     * 获取根资源ID
     * id最小的就是根资源
     */
    public Long getRootResourceId() {
        Query query = getSession().createQuery("select MIN(id) as id from " + Resource.class.getName());
        return (Long) query.uniqueResult();
    }

    /**
     * 根据资源主键删除资源角色关系
     * @param id 资源ID
     */
    public void deleteRoleResource(Long id) {
        Query query = getSession().createQuery("delete from " + RoleResourcePermission.class.getName() + " e where e.resourceId = :resourceId");
        query.setParameter("resourceId", id);
        query.executeUpdate();
    }
}
