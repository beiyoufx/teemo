/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.entity;

import com.alibaba.fastjson.annotation.JSONType;
import com.teemo.core.entity.BaseEntity;
import core.support.repository.EnabledQueryCache;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @author yongjie.teng
 * @date 16-11-21 下午5:47
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.entity
 */
@Entity
@Table(name = "permission")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@EnabledQueryCache
@JSONType(orders = {"id", "permissionKey", "permissionValue", "description", "available"})
public class Permission extends BaseEntity {
    private static final long serialVersionUID = 8805356697377561479L;
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * 权限key
     */
    @Column(name = "permission_key", length = 32, nullable = false, unique = true)
    private String permissionKey;

    /**
     * 权限权限名称
     */
    @Column(name = "permission_value", length = 32, nullable = false)
    private String permissionValue;

    /**
     * 权限描述
     */
    @Column(name = "description", length = 128)
    private String description;

    /**
     * 可用状态
     */
    @Column(name = "available")
    private Boolean available = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
