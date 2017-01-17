/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.support.repository;

import java.lang.annotation.*;

/**
 * 开启查询缓存
 * @author yongjie.teng
 * @date 17-1-17 上午9:53
 * @email yongjie.teng@foxmail.com
 * @package core.support.repository
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnabledQueryCache {
    boolean value() default true;
}
