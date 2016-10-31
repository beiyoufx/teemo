package core.dao;

import core.util.ReflectUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author yongjie.teng
 * @version 1.0
 * @date 16-10-27
 * @email yongjie.teng@foxmail.com
 * @package core.dao
 * @project teemo
 */
public abstract class BaseDao<E> implements Dao<E> {
    private SessionFactory sessionFactory;
    protected Class<E> entityClass;

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public BaseDao() {
        this.entityClass = ReflectUtil.findParameterizedType(getClass());
    }

    public void save(E entity) {
        getSession().save(entity);
    }

    public void delete(E entity) {
        getSession().delete(entity);
    }

    public boolean deleteById(Serializable... id) {
        boolean result = false;
        if ((id != null) && (id.length > 0)) {
            for (int i = 0; i < id.length; i++) {
                E entity = get(id[i]);
                if (entity != null) {
                    getSession().delete(entity);
                    result = true;
                }
            }
        }
        return result;
    }

    public void update(E entity) {
        getSession().update(entity);
    }

    public E get(Serializable id) {
        return getSession().get(entityClass, id);
    }

    public List<E> findAll() {
        Criteria criteria = getSession().createCriteria(this.entityClass);
        return criteria.list();
    }

    public Long countAll() {
        return (Long) getSession().createQuery("select count(*) from " + this.entityClass.getName()).uniqueResult();
    }
}
