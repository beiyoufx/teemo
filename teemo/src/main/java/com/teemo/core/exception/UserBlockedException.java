package com.teemo.core.exception;

/**
 * @author yongjie.teng
 * @date 16-11-21 下午7:31
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.core.exception
 */
public class UserBlockedException extends UserException {
    public UserBlockedException() {
        super("user.blocked", null);
    }
}
