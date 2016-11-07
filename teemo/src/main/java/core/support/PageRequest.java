/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.support;

import core.support.page.AbstractPageRequest;

/**
 * 分页条件实现类
 * @author yongjie.teng
 * @date 16-11-4 下午2:35
 * @email yongjie.teng@foxmail.com
 * @package core.support
 */
public class PageRequest extends AbstractPageRequest {
    private static final long serialVersionUID = 3818454816004846209L;

    public static PageRequest newPageRequest(int page, int size) {
        return new PageRequest(page, size);
    }

    public static PageRequest newPageRequest(int page, int size, Sort sort) {
        return new PageRequest(page, size, sort);
    }

    public PageRequest(int page, int size, Sort.Direction direction, String... properties) {
        super(page, size, new Sort(direction, properties));
    }

    public PageRequest(int page, int size, Sort sort) {
        super(page, size, sort);
    }

    public PageRequest(int page, int size) {
        super(page, size, null);
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "size=" + getPageSize() +
                ", page=" + getPageNumber() +
                ", sort=" + getSort() +
                '}';
    }
}
