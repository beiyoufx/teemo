/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.support.search;

import core.support.Condition;
import core.support.Sort;
import core.support.page.Pageable;
import core.util.StringUtil;

import java.io.Serializable;
import java.util.*;

/**
 * 查询条件抽象
 * @author yongjie.teng
 * @date 16-11-4 下午3:25
 * @email yongjie.teng@foxmail.com
 * @package core.support.search
 */
public abstract class AbstractSearchRequest implements Searchable, Serializable {
    private static final long serialVersionUID = -8020628529915246305L;

    private List<SearchFilter> filters = new LinkedList<SearchFilter>();
    private Sort sort;
    private Pageable page;

    public AbstractSearchRequest(List<SearchFilter> filters, Sort sort, Pageable page) {
        if (filters != null && !filters.isEmpty()) {
            this.filters = filters;
        }
        this.sort = sort;
        this.page = page;
    }

    @Override
    public Searchable addSearchParam(final String property, final Object value) {
        return addSearchFilter(Condition.newCondition(property, value));
    }

    @Override
    public Searchable addSearchParams(final Map<String, Object> searchParams) {
        if (searchParams == null || searchParams.isEmpty()) {
            return this;
        }
        for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            addSearchFilter(Condition.newCondition(key, value));
        }
        return this;
    }

    @Override
    public Searchable addSearchParam(final String property, final SearchOperator operator, final Object value) {
        return addSearchFilter(Condition.newCondition(property, operator, value));
    }

    @Override
    public Searchable addSearchFilter(final SearchFilter filter) {
        if (filter == null) {
            return this;
        }
        int index = this.filters.indexOf(filter);
        if (index == -1) {
            this.filters.add(filter);
        } else {
            this.filters.set(index, filter);
        }
        return this;
    }

    @Override
    public Searchable addSearchFilters(final Collection<? extends SearchFilter> filters) {
        if (filters == null || filters.isEmpty()) {
            return this;
        }

        for (SearchFilter filter : filters) {
            addSearchFilter(filter);
        }
        return this;
    }

    @Override
    public Collection<SearchFilter> getSearchFilters() {
        return Collections.unmodifiableCollection(this.filters);
    }

    @Override
    public boolean hasSearchFilter() {
        return !this.filters.isEmpty();
    }

    @Override
    public Searchable removeSearchFilter(final String property) {
        if (StringUtil.isEmpty(property)) {
            return this;
        }

        Iterator<SearchFilter> iterator = this.filters.iterator();
        while (iterator.hasNext()) {
            SearchFilter filter = iterator.next();
            if (property.equals(filter.getProperty())) {
                iterator.remove();
            }
        }

        return this;
    }

    @Override
    public Searchable removeSearchFilter(final String property, final SearchOperator operator) {
        if (StringUtil.isEmpty(property) || operator == null) {
            return this;
        }

        Iterator<SearchFilter> iterator = this.filters.iterator();
        while (iterator.hasNext()) {
            SearchFilter filter = iterator.next();
            if (property.equals(filter.getProperty()) && filter.getOperator() == operator) {
                iterator.remove();
            }
        }

        return this;
    }

    @Override
    public Sort getSort() {
        return this.sort;
    }

    @Override
    public Searchable addSort(Sort.Direction direction, String property) {
        if (this.sort != null) {
            this.sort = new Sort(direction, property);
        } else {
            this.sort = this.sort.and(new Sort(direction, property));
        }
        return this;
    }

    @Override
    public boolean hashSort() {
        return this.sort != null && this.sort.isNotEmpty();
    }

    @Override
    public void removeSort() {
        this.sort = null;
    }

    public abstract Searchable setPage(int pageNumber, int pageSize);

    /**
     * 当分页条件 {@link Pageable} 中包含排序 {@link Sort} 时，使用分页中的排序
     * @param page
     * @return
     */
    @Override
    public Searchable setPage(Pageable page) {
        this.page = page;
        if (page != null && page.getSort() != null && page.getSort().isNotEmpty()) {
            this.sort = page.getSort();
        }
        return this;
    }

    @Override
    public Pageable getPage() {
        return this.page;
    }

    @Override
    public boolean hasPageable() {
        return this.page != null && this.page.getPageSize() > 0;
    }

    @Override
    public void removePageable() {
        this.page = null;
    }

    @Override
    public Object getValue(String property) {
        if (StringUtil.isNotEmpty(property)) {
            for (SearchFilter filter : this.filters) {
                if (filter != null && property.equals(filter.getProperty())) {
                    return filter.getValue();
                }
            }
        }
        return null;
    }
}
