package com.teemo.core.exception;

/**
 * @author yongjie.teng
 * @date 16-11-21 下午7:23
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.core.exception
 */
public class UserNotExistsException extends UserException {
    public UserNotExistsException() {
        super("user.not.exists", null);
    }
}
