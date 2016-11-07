/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.support;

import core.support.search.*;
import core.util.StringUtil;

import java.util.*;

/**
 * WHERE过滤条件
 * 调用#setValue()方法有风险
 * @author yongjie.teng
 * @date 16-11-3 下午3:34
 * @email yongjie.teng@foxmail.com
 * @package core.support
 */
public class Condition extends AbstractSearchFilter {
    private static final long serialVersionUID = -5881232644192853247L;

    private List<SearchFilter> childConditions = new LinkedList<SearchFilter>();

    /**
     * 当操作符是一元操作符的时候, value可以为空
     * @param property
     * @param operator
     * @param value
     * @return 过滤条件 {@link Condition}
     */
    public static Condition newCondition(final SearchType type, final String property, final SearchOperator operator, final Object value) {
        return new Condition(type, property, operator, value);
    }

    /**
     * 默认条件连接类型为 AND (and)
     * @param property
     * @param operator
     * @param value
     * @return 过滤条件 {@link Condition}
     */
    public static Condition newCondition(final String property, final SearchOperator operator, final Object value) {
        return newCondition(SearchType.AND, property, operator, value);
    }

    /**
     * 默认操作符为 eq (=)
     * @param property
     * @param value
     * @return 过滤条件 {@link Condition}
     */
    public static Condition newCondition(final String property, final Object value) {
        return newCondition(property, SearchOperator.eq, value);
    }

    private Condition(final SearchType type, final String property, final SearchOperator operator, final Object value) {
        super(type, property, operator, value);
    }

    /**
     * 预加工value, 使其符合sql语句
     * @return Object
     */
    public Object getFormatValues() {
        SearchOperator operator = this.getOperator();
        Object value = this.getValue();
        if (operator == SearchOperator.like || operator == SearchOperator.notLike) {
            return "%" + value + "%";
        }
        if (operator == SearchOperator.prefixLike || operator == SearchOperator.prefixNotLike) {
            return value + "%";
        }

        if (operator == SearchOperator.suffixLike || operator == SearchOperator.suffixNotLike) {
            return "%" + value;
        }
        return value;
    }

    /**
     * @return 当前过滤条件为in过滤返回true否则false
     */
    public boolean isInFilter() {
        return SearchOperator.isAllowMultiValue(getOperator());
    }

    /**
     * @return 当前过滤条件为一元过滤返回true否则false
     */
    public boolean isUnaryFilter() {
        return SearchOperator.isAllowBlankValue(getOperator());
    }

    /**
     * 给当前过滤条件添加子条件，如or (a = 1 and b = 2)
     * @param property
     * @param operator
     * @param value
     * @return 过滤条件 {@link Condition}
     */
    public Condition addChildCondition(final String property, final SearchOperator operator, final Object value) {
        return addChildCondition(Condition.newCondition(property, operator, value));
    }

    public Condition addChildCondition(final SearchFilter filter) {
        if (filter == null) {
            return this;
        }
        int index = this.childConditions.indexOf(filter);
        if (index == -1) {
            this.childConditions.add(filter);
        } else {
            this.childConditions.set(index, filter);
        }
        return this;
    }

    public Condition addChildConditions(final Collection<? extends SearchFilter> childConditions) {
        if (childConditions == null || childConditions.isEmpty()) {
            return this;
        }

        for (SearchFilter filter : childConditions) {
            addChildCondition(filter);
        }
        return this;
    }

    public Condition removeChildCondition(final String property) {
        if (StringUtil.isEmpty(property)) {
            return this;
        }

        Iterator<SearchFilter> iterator = this.childConditions.iterator();
        while (iterator.hasNext()) {
            SearchFilter filter = iterator.next();
            if (property.equals(filter.getProperty())) {
                iterator.remove();
            }
        }

        return this;
    }

    public Condition removeChildCondition(final String property, final SearchOperator operator) {
        if (StringUtil.isEmpty(property) || operator == null) {
            return this;
        }

        Iterator<SearchFilter> iterator = this.childConditions.iterator();
        while (iterator.hasNext()) {
            SearchFilter filter = iterator.next();
            if (property.equals(filter.getProperty()) && filter.getOperator() == operator) {
                iterator.remove();
            }
        }

        return this;
    }

    @Override
    public boolean hasChildFilter() {
        return !this.childConditions.isEmpty();
    }

    @Override
    public Collection<SearchFilter> getChildFilters() {
        return Collections.unmodifiableCollection(this.childConditions);
    }

    @Override
    public String toString() {
        return "Condition{" +
                "type=" + getType() +
                ", property=" + getProperty() +
                ", operator=" + getOperator() +
                ", value=" + getValue() +
                "}";
    }
}
