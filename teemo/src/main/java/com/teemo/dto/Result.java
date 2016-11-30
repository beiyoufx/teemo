/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.dto;

import java.io.Serializable;

/**
 * @author yongjie.teng
 * @date 16-11-7 下午7:07
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.dto
 */
public class Result implements Serializable {
    private int code;
    private String message;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
