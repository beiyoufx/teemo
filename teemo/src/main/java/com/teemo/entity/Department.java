package com.teemo.entity;

import com.teemo.core.entity.BaseEntity;
import com.teemo.core.entity.LogicDeletable;

import javax.persistence.*;

/**
 * @author yongjie.teng
 * @version 1.0
 * @date 16-10-21
 * @email yongjie.teng@zkh360.com
 * @package com.teemo.entity
 * @project teemo
 */
@Entity
@Table(name = "department")
public class Department extends BaseEntity implements LogicDeletable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "department_key", length = 8, nullable = false, unique = true)
    private String departmentKey;
    @Column(name = "department_value", length = 32, nullable = false)
    private String departmentValue;
    @Column(name = "description", length = 128)
    private String description;
    @Column(name = "parent_department_key")
    private String parentDepartmentKey;

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

    public String getParentDepartmentKey() {
        return parentDepartmentKey;
    }

    public void setParentDepartmentKey(String parentDepartmentKey) {
        this.parentDepartmentKey = parentDepartmentKey;
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
