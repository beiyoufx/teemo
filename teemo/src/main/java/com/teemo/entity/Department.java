/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.entity;

import com.alibaba.fastjson.annotation.JSONType;
import com.teemo.core.entity.BaseEntity;
import com.teemo.core.entity.LogicDeletable;
import core.support.repository.EnabledQueryCache;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @author yongjie.teng
 * @version 1.0
 * @date 16-10-21
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.entity
 * @project teemo
 */
@Entity
@Table(name = "department")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@EnabledQueryCache
@JSONType(orders = {"id", "departmentKey", "departmentValue", "parentId", "description"})
public class Department extends BaseEntity implements LogicDeletable {
    private static final long serialVersionUID = -359007150299161832L;
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * 部门key
     */
    @Column(name = "department_key", length = 32, nullable = false, unique = true)
    private String departmentKey;

    /**
     * 部门名称
     */
    @Column(name = "department_value", length = 32, nullable = false)
    private String departmentValue;

    /**
     * 部门描述
     */
    @Column(name = "description", length = 128)
    private String description;

    /**
     * 上级部门ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentKey() {
        return departmentKey;
    }

    public void setDepartmentKey(String departmentKey) {
        this.departmentKey = departmentKey;
    }

    public String getDepartmentValue() {
        return departmentValue;
    }

    public void setDepartmentValue(String departmentValue) {
        this.departmentValue = departmentValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    private Boolean deleted = Boolean.FALSE;
    @Override
    public Boolean getDeleted() {
        return this.deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public void markDeleted() {
        this.deleted = Boolean.TRUE;
    }
}
