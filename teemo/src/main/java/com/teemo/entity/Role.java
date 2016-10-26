package com.teemo.entity;

import com.teemo.core.entity.BaseEntity;

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
@Table(name = "role")
public class Role extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "role_key", length = 8, nullable = false, unique = true)
    private String roleKey;
    @Column(name = "role_value", length = 32, nullable = false)
    private String roleValue;
    @Column(name = "description", length = 128)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
