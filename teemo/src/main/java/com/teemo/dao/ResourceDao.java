package com.teemo.dao;

import com.teemo.entity.Resource;
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
        Query query = this.getSession().createQuery("select MIN(id) as id from " + Resource.class.getName());
        return (Long) query.uniqueResult();
    }
}
