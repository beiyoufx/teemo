package core.service;

import core.dao.Dao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author yongjie.teng
 * @version 1.0
 * @date 16-10-27
 * @email yongjie.teng@foxmail.com
 * @package core.service
 * @project teemo
 */
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class})
public abstract class BaseService<E> implements Service<E> {
    protected Dao<E> dao;

    public void save(E entity) {
        this.dao.save(entity);
    }

    public void delete(E entity) {
        this.dao.delete(entity);
    }

    public boolean deleteById(Serializable... id) {
        return this.dao.deleteById(id);
    }

    public void update(E entity) {
        this.dao.update(entity);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public E get(Serializable id) {
        return this.dao.get(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<E> findAll() {
        return this.dao.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Long countAll() {
        return this.dao.countAll();
    }
}
