/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.dto;

/**
 * @author yongjie.teng
 * @date 16-11-24 下午6:49
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.dto
 */
public class ResultData extends Result {
    private Object data;
    public ResultData(int code, String message) {
        super(code, message);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
