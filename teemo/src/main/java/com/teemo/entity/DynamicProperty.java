/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.teemo.core.entity.BaseEntity;
import com.teemo.core.entity.LogicDeletable;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yongjie.teng
 * @date 16-10-31 下午2:57
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.entity
 */
@Entity
@Table(name = "dynamic_property")
public class DynamicProperty extends BaseEntity implements LogicDeletable {
    private static final long serialVersionUID = 570799249720526949L;
    /**
     * 自增主键ID
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * 动态属性key，逻辑上唯一，数据库中存在同key的多版本记录
     */
    @Column(name = "key", nullable = false, length = 64)
    private String key;

    /**
     * 动态属性值
     */
    @Column(name = "value", nullable = false, length = 128)
    private String value;

    /**
     * 创建或更新该记录的用户名
     */
    @Column(name = "author", length = 32)
    private String author;

    /**
     * 创建该动态属性的时间
     */
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createTime;

    /**
     * 上次修改该属性的时间
     */
    @Column(name = "modify_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date modifyTime;

    /**
     * 动态属性的版本号
     */
    @Column(name = "version")
    private Float version;

    /**
     * 动态属性的逻辑删除状态
     */
    @Column(name = "deleted")
    @JSONField(serialize = false)
    private Boolean deleted = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Float getVersion() {
        return version;
    }

    public void setVersion(Float version) {
        this.version = version;
    }

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
