package com.teemo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.teemo.core.entity.BaseEntity;
import com.teemo.core.entity.LogicDeletable;

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
public class User extends BaseEntity implements LogicDeletable {
    /**
     * 自增主键ID
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * 用户名（不可用于登陆，不唯一）
     */
    @Column(name = "username", length = 32, nullable = false)
    private String username;

    /**
     * 用户密码
     */
    @Column(name = "password", length = 32, nullable = false)
    @JSONField(serialize = false)
    private String password;
    /**
     * 加密密码时用的盐
     */
    @Column(name = "salt", length = 8, nullable = false)
    @JSONField(serialize = false)
    private String salt;

    /**
     * 用户邮箱（用于登陆，唯一）
     */
    @Column(name = "email", length = 32, nullable = false, unique = true)
    private String email;

    /**
     * 用户手机号码
     */
    @Column(name = "mobile_phone", length = 16, nullable = false)
    private String mobilePhone;

    /**
     * 用户所属部门的key
     */
    @Column(name = "department_key", length = 8)
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
    @JSONField(serialize = false)
    private Boolean deleted = Boolean.FALSE;

    /**
     * 用户的角色关系
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
