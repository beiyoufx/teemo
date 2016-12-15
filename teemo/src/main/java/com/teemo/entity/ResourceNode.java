/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.entity;

import com.alibaba.fastjson.annotation.JSONType;

import java.io.Serializable;
import java.util.List;

/**
 * 角色授权时的资源节点模型
 * @author yongjie.teng
 * @date 16-12-13 下午2:26
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.entity
 */
@JSONType(orders = {"id", "key", "value", "children"})
public class ResourceNode implements Serializable {
    private static final long serialVersionUID = 4683616209006957086L;
    /**
     * 资源主键
     */
    private Long id;

    /**
     * 资源key
     */
    private String key;

    /**
     * 资源名称
     */
    private String value;

    /**
     * 子资源节点
     */
    private List<ResourceNode> children;

    /**
     * 末尾资源节点权限状态
     */
    List<PermissionState> permissionStates;

    public ResourceNode() {
        super();
    }

    public ResourceNode(Long id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

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

    public List<ResourceNode> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceNode> children) {
        this.children = children;
    }

    public List<PermissionState> getPermissionStates() {
        return permissionStates;
    }

    public void setPermissionStates(List<PermissionState> permissionStates) {
        this.permissionStates = permissionStates;
    }

    /**
     * 角色授权时的资源权限模型
     */
    public class PermissionState extends Permission {
        private static final long serialVersionUID = 2885977237676604019L;

        /**
         * 当前角色是否有操作该资源的权限
         */
        private Boolean authorized = Boolean.FALSE;

        public PermissionState(Permission permission, Boolean authorized) {
            this.authorized = authorized;
            this.setId(permission.getId());
            this.setAvailable(permission.getAvailable());
            this.setDescription(permission.getDescription());
            this.setPermissionKey(permission.getPermissionKey());
            this.setPermissionValue(permission.getPermissionValue());
        }

        public Boolean getAuthorized() {
            return authorized;
        }

        public void setAuthorized(Boolean authorized) {
            this.authorized = authorized;
        }
    }
}
