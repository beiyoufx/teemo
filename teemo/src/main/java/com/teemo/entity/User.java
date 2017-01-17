/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.teemo.core.entity.BaseEntity;
import com.teemo.core.entity.LogicDeletable;
import core.support.repository.EnabledQueryCache;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户信息
 * @author yongjie.teng
 * @version 1.0
 * @date 16-10-21
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.entity
 * @project teemo
 */
@Entity
@Table(name = "user")
@DynamicInsert(value = true)
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@EnabledQueryCache
@JSONType(orders = {"id", "username", "nickname", "email", "mobilePhone", "status", "departmentKey", "createTime", "modifyTime", "roles"},
            ignores = {"password", "salt", "deleted"})
public class User extends BaseEntity implements LogicDeletable {
    private static final long serialVersionUID = 2225038507614711877L;
    /**
     * 自增主键ID
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * 用户名（可用于登陆，唯一）
     */
    @Column(name = "username", length = 32, nullable = false)
    private String username;

    /**
     * 用户昵称（不可用于登陆，不唯一）
     */
    @Column(name = "nickname", length = 32, nullable = false)
    private String nickname;

    /**
     * 用户密码
     */
    @Column(name = "password", length = 32, nullable = false)
    private String password;
    /**
     * 加密密码时用的盐
     */
    @Column(name = "salt", length = 8, nullable = false)
    private String salt;

    /**
     * 用户邮箱（用于登陆，唯一）
     */
    @Column(name = "email", length = 32, nullable = false, unique = true)
    private String email;

    /**
     * 用户手机号码
     */
    @Column(name = "mobile_phone", length = 16)
    private String mobilePhone;

    /**
     * 用户所属部门的key
     */
    @Column(name = "department_key", length = 32)
    private String departmentKey;

    /**
     * 创建该用户的时间
     */
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createTime;

    /**
     * 上次变更用户信息的时间
     */
    @Column(name = "modify_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date modifyTime;

    /**
     * 用户的逻辑删除状态
     */
    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    /**
     * 用户状态
     */
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.normal;

    /**
     * 用户的角色关系
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    @Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Role> roles = new HashSet<Role>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getDepartmentKey() {
        return departmentKey;
    }

    public void setDepartmentKey(String departmentKey) {
        this.departmentKey = departmentKey;
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
