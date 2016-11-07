/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.service;

import core.support.Sort;
import core.support.search.Searchable;

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
public interface Service<E> {
    /**
     * 保存实体并返回ID
     * @see org.hibernate.Session#save(Object)
     * @param entity
     */
    public Serializable save(E entity);

    /**
     * 保存实体
     * @see org.hibernate.Session#persist(Object)
     * @param entity
     */
    public void persist(E entity);

    /**
     * 删除实体
     * @see org.hibernate.Session#delete(Object)
     * @param entity
     */
    public void delete(E entity);

    /**
     * 根据实体ID进行批量删除
     * @param id
     * @return
     */
    public boolean deleteById(Serializable... id);

    /**
     * 更新实体
     * @see org.hibernate.Session#update(Object)
     * @param entity
     */
    public void update(E entity);

    /**
     * 根据给定条件更新指定字段
     * @param searchable 检索条件
     * @param propName 更新字段的属性名称
     * @param propValue 更新字段的属性值
     */
    public void update(Searchable searchable, String[] propName, Object[] propValue);

    /**
     * 根据给定条件更新指定字段
     * @param searchable 检索条件
     * @param propName 更新字段的属性名称
     * @param propValue 更新字段的属性值
     */
    public void update(Searchable searchable, String propName, Object propValue);

    /**
     * 合并给定的实体状态到持久化上下文
     * @see org.hibernate.Session#merge(Object)
     * @param entity
     * @return 合并后的实体
     */
    public E merge(E entity);

    /**
     * 清除会话
     * @see org.hibernate.Session#clear()
     */
    public void clear();

    /**
     * 从持久化上下文中删除给定实体
     * @see org.hibernate.Session#evict(Object)
     * @param entity
     * @throws NullPointerException if the passed object is {@code null}
     * @throws IllegalArgumentException if the passed object is not defined as an entity
     */
    public void evict(E entity);

    /**
     * 根据实体ID获取单个实体
     * @param id
     * @return 实体
     */
    public E get(Serializable id);

    /**
     * 根据ID延迟加载持久化对象
     * @see org.hibernate.Session#load(String, java.io.Serializable)
     * @param id
     * @return 实体
     */
    public E load(Serializable id);

    /**
     * 根据给定查询条件获取单个实体
     * @param searchable
     * @return 实体
     */
    public E get(Searchable searchable);

    /**
     * 根据属性名获取单个实体
     * @param propName 属性名称
     * @param propValue 属性值
     * @return 实体
     */
    public E get(String propName, Object propValue);

    /**
     * 根据属性列表获取单个实体
     * @param searchParams 属性列表
     * @return 实体
     */
    public E get(Map<String, Object> searchParams);

    /**
     * 查询实体所有数目
     * @return 实体所有数目
     */
    public Long countAll();

    /**
     * 查询所有实体
     * @param sort 排序
     * @param maxResults 需要返回的最大行数
     * @return 实体列表
     */
    public List<E> findAll(Sort sort, Integer maxResults);

    /**
     * 查询所有实体
     * @param maxResults 需要返回的最大行数
     * @return 实体列表
     */
    public List<E> findAll(Integer maxResults);

    /**
     * 查询所有实体
     * @return 实体列表
     */
    public List<E> findAll();

    /**
     * 根据查询条件获取实体列表
     * @param searchable 查询条件
     * @return 实体列表
     */
    public List<E> find(Searchable searchable);

    /**
     * 根据属性列表获取实体列表
     * @param searchParams 属性列表
     * @return 实体列表
     */
    public List<E> find(Map<String, Object> searchParams);

    /**
     * 根据属性获取实体列表
     * @param propName 属性名
     * @param propValue 属性值
     * @return 实体列表
     */
    public List<E> find(String propName, Object propValue);
}
