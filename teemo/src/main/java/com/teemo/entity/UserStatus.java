/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.entity;

/**
 * @author yongjie.teng
 * @date 16-11-21 下午5:38
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.entity
 */
public enum UserStatus {
    normal("正常状态"),blocked("锁定状态");

    private final String info;

    private UserStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
