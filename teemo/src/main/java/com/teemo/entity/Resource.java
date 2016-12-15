/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.entity;

import com.alibaba.fastjson.annotation.JSONType;
import com.teemo.core.entity.BaseEntity;

import javax.persistence.*;

/**
 * @author yongjie.teng
 * @date 16-11-21 下午5:54
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.entity
 */
@Entity
@Table(name = "resource")
@JSONType(orders = {"id", "resourceKey", "resourceValue", "url", "parentId", "parentIds", "available"})
public class Resource extends BaseEntity {
    private static final long serialVersionUID = -7686411622173094721L;
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * 资源key
     */
    @Column(name = "resource_key", length = 128)
    private String resourceKey;

    /**
     * 资源名称
     */
    @Column(name = "resource_value", length = 128, nullable = false)
    private String resourceValue;

    /**
     * 资源标识地址
     */
    @Column(name = "url", length = 256)
    private String url;

    /**
     * 上级资源ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 所有上级资源ID
     * 分隔符是 /
     */
    @Column(name = "parent_ids", length = 256)
    private String parentIds;

    /**
     * 可用状态
     */
    @Column(name = "available")
    private Boolean available = Boolean.FALSE;

    /**
     * 资源类型
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ResourceType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getResourceValue() {
        return resourceValue;
    }

    public void setResourceValue(String resourceValue) {
        this.resourceValue = resourceValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }
}
