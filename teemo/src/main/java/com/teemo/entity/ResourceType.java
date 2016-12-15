/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.entity;

/**
 * @author yongjie.teng
 * @date 16-12-14 下午7:11
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.entity
 */
public enum ResourceType {
    view("视图"),menu("菜单"),entity("实体"),url("链接");

    private final String info;

    private ResourceType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
