/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.service;

import core.dao.Dao;
import core.support.Sort;
import core.support.search.Searchable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author yongjie.teng
 * @version 1.0
 * @date 16-10-27
 * @email yongjie.teng@foxmail.com
 * @package core.service
 * @project teemo
 */
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
public abstract class BaseService<E> implements Service<E> {
    protected Dao<E> dao;

    @Override
    public Serializable save(E entity) {
        return this.dao.save(entity);
    }

    @Override
    public void persist(E entity) {
        this.dao.persist(entity);
    }

    @Override
    public void delete(E entity) {
        this.dao.delete(entity);
    }

    @Override
    public boolean deleteById(Serializable... id) {
        return this.dao.deleteById(id);
    }

    @Override
    public void update(E entity) {
        this.dao.update(entity);
    }

    @Override
    public void update(Searchable searchable, String[] propName, Object[] propValue) {
        this.dao.update(searchable, propName, propValue);
    }

    @Override
    public void update(Searchable searchable, String propName, Object propValue) {
        this.dao.update(searchable, propName, propValue);
    }

    @Override
    public E merge(E entity) {
        return this.dao.merge(entity);
    }

    @Override
    public void clear() {
        this.dao.clear();
    }

    @Override
    public void evict(E entity) {
        this.dao.evict(entity);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public E get(Serializable id) {
        return this.dao.get(id);
    }

    @Override
    public E load(Serializable id) {
        return this.dao.load(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public E get(Searchable searchable) {
        return this.dao.get(searchable);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public E get(String propName, Object propValue) {
        return this.dao.get(propName, propValue);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public E get(Map<String, Object> searchParams) {
        return this.dao.get(searchParams);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Long countAll() {
        return this.dao.countAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<E> findAll(Sort sort, Integer maxResults) {
        return this.dao.findAll(sort, maxResults);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<E> findAll(Integer maxResults) {
        return this.dao.findAll(maxResults);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<E> findAll() {
        return this.dao.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<E> find(Searchable searchable) {
        return this.dao.find(searchable);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<E> find(Map<String, Object> searchParams) {
        return this.dao.find(searchParams);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<E> find(String propName, Object propValue) {
        return this.dao.find(propName, propValue);
    }
}
