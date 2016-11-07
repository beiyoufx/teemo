/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.support;

import core.support.page.Pageable;
import core.support.search.AbstractSearchRequest;
import core.support.search.SearchFilter;
import core.support.search.Searchable;

import java.util.List;

/**
 * 查询条件实现类，包括过滤条件、分页条件、排序
 * @author yongjie.teng
 * @date 16-11-3 下午6:51
 * @email yongjie.teng@foxmail.com
 * @package core.support
 */
public class SearchRequest extends AbstractSearchRequest {
    private static final long serialVersionUID = 7126784443721187450L;

    public static SearchRequest newSearchRequest(List<SearchFilter> filters, Sort sort, Pageable page) {
        return new SearchRequest(filters, sort, page);
    }

    public static SearchRequest newSearchRequest(Sort sort, Pageable page) {
        return new SearchRequest(sort, page);
    }

    public static SearchRequest newSearchRequest() {
        return new SearchRequest();
    }

    public SearchRequest(List<SearchFilter> filters, Sort sort, Pageable page) {
        super(filters, sort, page);
    }

    public SearchRequest(Sort sort, Pageable page) {
        this(null, sort, page);
    }

    public SearchRequest() {
        this(null, null, null);
    }

    @Override
    public Searchable setPage(int pageNumber, int pageSize) {
        return setPage(new PageRequest(pageNumber, pageSize));
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "filters=" + getSearchFilters() +
                ", page=" + getPage() +
                ", sort=" + getSort() +
                '}';
    }
}
