/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.support.search;

import core.support.Sort;
import core.support.page.Pageable;

import java.util.Collection;
import java.util.Map;

/**
 * 查询条件 {@link Searchable}接口
 * @author yongjie.teng
 * @date 16-11-3 下午5:58
 * @email yongjie.teng@foxmail.com
 * @package core.support.search
 */
public interface Searchable {

    /**
     * 添加过滤条件 {@link SearchFilter}
     * 如果添加时不加操作符, 默认操作应该是 eq (=)
     * @param property 需要过滤的属性名
     * @param value 给定的属性值
     * @return 查询条件 {@link Searchable}
     */
    public Searchable addSearchParam(final String property, final Object value);

    /**
     * 添加一组查询参数来添加一组过滤条件 {@link SearchFilter}
     * @param searchParams 查询参数, key是属性名, value是属性值
     * @return 查询条件 {@link Searchable}
     */
    public Searchable addSearchParams(final Map<String, Object> searchParams);

    /**
     * 通过指定的参数添加过滤条件 {@link SearchFilter}
     * @param property 需要过滤的属性名
     * @param operator 操作符
     * @param value 给定的属性值
     * @return 查询条件 {@link Searchable}
     */
    public Searchable addSearchParam(final String property, final SearchOperator operator, final Object value);

    /**
     * 添加过滤条件 {@link SearchFilter}
     * @param filter 过滤条件 {@link SearchFilter}
     * @return 查询条件 {@link Searchable}
     */
    public Searchable addSearchFilter(final SearchFilter filter);

    /**
     * 添加多个and连接的过滤条件 {@link SearchFilter}
     * @param filters 过滤条件 {@link SearchFilter}
     * @return 查询条件 {@link Searchable}
     */
    public Searchable addSearchFilters(final Collection<? extends SearchFilter> filters);

    /**
     * 获取查询过滤条件 {@link SearchFilter}
     * @return 过滤条件 {@link SearchFilter}的集合
     */
    public Collection<SearchFilter> getSearchFilters();

    /**
     * 是否有过滤条件 {@link SearchFilter}
     * @return 当且仅当过滤条件 {@link SearchFilter}个数大于0才返回true
     */
    public boolean hasSearchFilter();

    /**
     * 移除指定属性对应的所有过滤条件 {@link SearchFilter}
     * @param property 目标过滤条件 {@link SearchFilter}的属性名
     * @return 查询条件 {@link Searchable}
     */
    public Searchable removeSearchFilter(final String property);

    /**
     * 移除指定属性和操作符的过滤条件 {@link SearchFilter}
     * @param property 目标过滤条件 {@link SearchFilter}的属性名
     * @param operator 目标过滤条件 {@link SearchFilter}的操作符
     * @return 查询条件 {@link Searchable}
     */
    public Searchable removeSearchFilter(String property, SearchOperator operator);

    /**
     * 获取排序信息
     * @return 排序条件 {@link Sort}
     */
    public Sort getSort();

    /**
     * 通过指定参数添加排序条件 {@link Sort}
     * @param direction 排序方向
     * @param property 排序所根据的属性
     * @return 查询条件 {@link Searchable}
     */
    public Searchable addSort(final Sort.Direction direction, String property);

    /**
     * 是否有排序条件 {@link Sort}
     * @return 当且仅当排序条件 {@link Sort}个数大于0才返回true
     */
    public boolean hashSort();

    /**
     * 移除所有排序条件 {@link Sort}
     */
    public void removeSort();

    /**
     * 通过指定参数添加分页条件 {@link Pageable}
     * @param pageNumber 分页页码 索引从 0 开始
     * @param pageSize 每页大小
     * @return 查询条件 {@link Searchable}
     */
    public Searchable setPage(final int pageNumber, final int pageSize);

    /**
     * 添加分页条件 {@link Pageable}
     * @param page
     * @return 查询条件 {@link Searchable}
     */
    public Searchable setPage(Pageable page);

    /**
     * 获取分页和排序信息
     * @return 分页条件 {@link Pageable}
     */
    public Pageable getPage();

    /**
     * 是否有分页条件 {@link Pageable}
     * @return 有分页条件 {@link Pageable}时返回true
     */
    public boolean hasPageable();

    /**
     * 移除所有分页条件 {@link Pageable}
     */
    public void removePageable();

    /**
     * 获取过滤条件 {@link SearchFilter}中给定属性对应的值
     * @param property 要查找的属性
     * @return 过滤条件 {@link SearchFilter}中给定属性对应的值
     */
    public Object getValue(final String property);
}
