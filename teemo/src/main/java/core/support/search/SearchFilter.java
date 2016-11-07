/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.support.search;

import java.util.Collection;

/**
 * 过滤条件接口
 * @author yongjie.teng
 * @date 16-11-3 下午3:27
 * @email yongjie.teng@foxmail.com
 * @package core.support.search
 */
public interface SearchFilter {
    /**
     * 获取条件连接类型
     * @return 条件连接类型 {@link SearchType}
     */
    public SearchType getType();

    /**
     * 获取需要过滤的属性名
     * @return 属性名
     */
    public String getProperty();

    /**
     * 获取操作符
     * @return 操作符
     */
    public SearchOperator getOperator();

    /**
     * 获取给定的属性值
     * @return 属性值
     */
    public Object getValue();

    /**
     * 有无子过滤条件，如or (a = 1 and b = 2)
     * @return boolean
     */
    public boolean hasChildFilter();

    /**
     * 获取子过滤条件
     * @return
     */
    public Collection<SearchFilter> getChildFilters();
}
