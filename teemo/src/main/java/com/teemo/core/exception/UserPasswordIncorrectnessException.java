package com.teemo.core.exception;

/**
 * @author yongjie.teng
 * @date 16-11-21 下午7:30
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.core.exception
 */
public class UserPasswordIncorrectnessException extends UserException {
    public UserPasswordIncorrectnessException() {
        super("user.password.incorrectness", null);
    }
}
