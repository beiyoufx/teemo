/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.util;

import javax.persistence.Table;

/**
 * @author yongjie.teng
 * @date 16-10-31 下午5:28
 * @email yongjie.teng@foxmail.com
 * @package core.util
 */
public class EntityUtil {
    /**
     * 获取使用JPA注解的实体类所对应的表名
     * @param clazz
     * @return {@link String}
     */
    private static String getTableName(Class clazz) {
        Table annotation = (Table)clazz.getAnnotation(Table.class);
        if(annotation != null){
            return annotation.name();
        }
        return null;
    }
}
