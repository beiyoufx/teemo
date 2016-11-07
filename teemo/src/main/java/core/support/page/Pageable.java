/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.support.page;

import core.support.Sort;

/**
 * 分页条件接口
 * @author yongjie.teng
 * @date 16-11-3 下午6:00
 * @email yongjie.teng@foxmail.com
 * @package core.support.page
 */
public interface Pageable {
    /**
     * 起始页码为0
     * @return 当前页码 int
     */
    public int getPageNumber();

    /**
     * @return 每页条目大小 int
     */
    public int getPageSize();

    /**
     * @return 页码偏移量
     */
    public int getOffset();

    /**
     * @return 排序条件 {@link Sort}
     */
    public Sort getSort();

    /**
     * 如果当前页是第一页则返回false否则返回true
     *  @return boolean
     */
    public boolean hasPrevious();
}
