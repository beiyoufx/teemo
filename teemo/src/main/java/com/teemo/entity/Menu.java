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
 * 菜单模型
 * 最后一级菜单才有URL参数
 * @author yongjie.teng
 * @date 16-12-27 下午2:27
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.entity
 */
@JSONType(orders = {"menuKey", "menuValue", "url", "sequence", "children"})
public class Menu implements Serializable {
    private static final long serialVersionUID = -5667103380851357787L;
    /**
     * 菜单Key
     */
    private String menuKey;

    /**
     * 菜单名称
     */
    private String menuValue;

    /**
     * 菜单地址
     */
    private String url;

    /**
     * 菜单字体图标
     */
    private String menuIcon;

    /**
     * 顺序
     */
    private Integer sequence;

    /**
     * 子级菜单
     */
    private List<Menu> children;

    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public String getMenuValue() {
        return menuValue;
    }

    public void setMenuValue(String menuValue) {
        this.menuValue = menuValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    /**
     * 是否有子级菜单
     * @return boolean
     */
    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }
}
