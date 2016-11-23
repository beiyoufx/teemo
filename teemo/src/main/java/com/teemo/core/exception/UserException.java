/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.core.exception;

import core.util.SpringUtil;
import core.util.StringUtil;

/**
 * @author yongjie.teng
 * @date 16-11-7 下午5:28
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.core.exception
 */
public class UserException extends Exception {
    private String code;
    private String defaultMessage;

    public UserException(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getMessage() {
        String message = null;
        if (StringUtil.isNotEmpty(this.code)) {
            message = SpringUtil.getMessage(code);
        }
        if (message == null) {
            message = this.defaultMessage;
        }
        return message;
    }

}
