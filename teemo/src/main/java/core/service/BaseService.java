package core.service;

import core.dao.Dao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yongjie.teng
 * @version 1.0
 * @date 16-10-27
 * @email yongjie.teng@foxmail.com
 * @package core.service
 * @project teemo
 */
public abstract class BaseService<E> implements Service<E> {
    protected Dao<E> dao;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<E> findAll() {
        return this.dao.findAll();
    }

    public Long countAll() {
        return this.dao.countAll();
    }
}
