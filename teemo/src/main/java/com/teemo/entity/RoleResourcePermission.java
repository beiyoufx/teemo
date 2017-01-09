package com.teemo.entity;

import com.teemo.core.entity.BaseEntity;
import core.support.repository.CollectionToStringUserType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Set;

/**
 * 在数据库中使用字符串存储权限集合不使用关联表
 * @author yongjie.teng
 * @date 16-11-22 下午4:22
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.entity
 */
@TypeDef(
    name = "SetToStringUserType",
    typeClass = CollectionToStringUserType.class,
    parameters = {
        @Parameter(name = "separator", value = ","),
        @Parameter(name = "collectionType", value = "java.util.HashSet"),
        @Parameter(name = "elementType", value = "java.lang.Long")
    }
)
@Entity
@Table(name = "role_resource_permission")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleResourcePermission extends BaseEntity {
    private static final long serialVersionUID = 4968928107546858204L;
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * 角色
     * 作为一列存在
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    /**
     * 权限主键集合
     * 存储格式为：id1,id2,id3
     */
    @Column(name = "permission_ids")
    @Type(type = "SetToStringUserType")
    private Set<Long> permissionIds;


    /**
     * 资源主键
     */
    @Column(name = "resource_id")
    private Long resourceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Set<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Set<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
