/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * @author yongjie.teng
 * @date 16-11-3 下午2:48
 * @email yongjie.teng@foxmail.com
 * @package core.exception
 */
public class SearchException extends NestedRuntimeException {

    public SearchException(String msg) {
        super(msg);
    }

    public SearchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
