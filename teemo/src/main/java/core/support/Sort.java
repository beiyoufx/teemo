/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.support;

import core.util.StringUtil;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 排序条件
 * 参考Spring data jpa Sort实现
 * @author yongjie.teng
 * @date 16-11-1 下午7:26
 * @email yongjie.teng@foxmail.com
 * @package core.support
 */
public class Sort implements Iterable<Sort.Order>, Serializable {
    private static final long serialVersionUID = -6056743972633766976L;
    public static final Direction DEFAULT_DIRECTION = Direction.asc;
    private final List<Order> orders;

    /**
     * 使用给定的 {@link Order}s 实例化一个 {@link Sort}.
     *
     * @param orders must not be {@literal null}.
     */
    public Sort(Order... orders) {
        this(Arrays.asList(orders));
    }

    /**
     * 实例化一个 {@link Sort}.
     *
     * @param orders must not be {@literal null} or contain {@literal null}.
     */
    public Sort(List<Order> orders) {

        if (null == orders || orders.isEmpty()) {
            throw new IllegalArgumentException("You have to provide at least one sort property to sort by!");
        }

        this.orders = orders;
    }

    /**
     * 使用默认排序方向 {@link Direction#asc} 实例化一个 {@link Sort}.
     *
     * @param properties must not be {@literal null} or contain {@literal null} or empty strings
     */
    public Sort(String... properties) {
        this(DEFAULT_DIRECTION, properties);
    }

    /**
     * 根据给定的排序方向和属性实例化一个 {@link Sort}.
     *
     * @param direction defaults to {@link Sort#DEFAULT_DIRECTION} (for {@literal null} cases, too)
     * @param properties must not be {@literal null}, empty or contain {@literal null} or empty strings.
     */
    public Sort(Direction direction, String... properties) {
        this(direction, properties == null ? new ArrayList<String>() : Arrays.asList(properties));
    }

    /**
     * 根据给定的排序方向和属性实例化一个 {@link Sort}.
     *
     * @param direction defaults to {@link Sort#DEFAULT_DIRECTION} (for {@literal null} cases, too)
     * @param properties must not be {@literal null} or contain {@literal null} or empty strings.
     */
    public Sort(Direction direction, List<String> properties) {

        if (properties == null || properties.isEmpty()) {
            throw new IllegalArgumentException("You have to provide at least one property to sort by!");
        }

        this.orders = new ArrayList<Order>(properties.size());

        for (String property : properties) {
            this.orders.add(new Order(direction, property));
        }
    }

    /**
     * 将给定的 {@link Sort} 中的 {@link Order}s 与当前的 {@link Sort} 结合并返回新的 {@link Sort}
     *
     * @param sort can be {@literal null}.
     * @return {@link Sort}
     */
    public Sort and(Sort sort) {

        if (sort == null) {
            return this;
        }

        ArrayList<Order> these = new ArrayList<Order>(this.orders);

        for (Order order : sort) {
            these.add(order);
        }

        return new Sort(these);
    }

    /**
     * 返回指定属性的排序方向.
     *
     * @param property
     * @return {@link Sort.Order}
     */
    public Order getOrderFor(String property) {

        for (Order order : this) {
            if (order.getProperty().equals(property)) {
                return order;
            }
        }

        return null;
    }

    /**
     * 返回 {@link Sort} 中 {@link Order} 的数目
     *
     * @return {@link Sort}
     */
    public int size() {
        return this.orders.size();
    }

    /**
     * 当排序条件 {@link Order} 列表中不包含元素时返回true
     * @return boolean
     */
    public boolean isEmpty() {
        return this.orders.isEmpty();
    }

    /**
     * 当排序条件 {@link Order} 列表中包含元素时返回true
     * @return boolean
     */
    public boolean isNotEmpty() {
        return !this.orders.isEmpty();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Order> iterator() {
        return this.orders.iterator();
    }

    /*
 * (non-Javadoc)
 * @see java.lang.Object#equals(java.lang.Object)
 */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Sort)) {
            return false;
        }

        Sort that = (Sort) obj;

        return this.orders.equals(that.orders);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        int result = 17;
        result = 31 * result + orders.hashCode();
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return StringUtils.collectionToCommaDelimitedString(orders);
    }

    public static enum Direction {
        asc, desc;

        /**
         * 通过一个给定的 {@link String} 值来返回 {@link Direction} enum.
         * @param value
         * @throws IllegalArgumentException 在给定的String不能解析为Direction时抛出
         * @return {@link Direction}
         */
        public static Direction fromString(String value) {

            try {
                return Direction.valueOf(value.toUpperCase(Locale.US));
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format(
                        "Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
            }
        }
    }

    public static class Order implements Serializable {
        private static final long serialVersionUID = -4603422140888624728L;
        private final Direction direction;
        private final String property;

        public Order(Direction direction, String property) {
            if (StringUtil.isEmpty(property)) {
                throw new IllegalArgumentException("Property must not null or empty!");
            }
            this.direction = direction;
            this.property = property;
        }

        public Order(String property) {
            this(Sort.DEFAULT_DIRECTION, property);
        }

        /**
         * 返回当前 {@link Order} 的属性所对应的排序方向.
         *
         * @return {@link Direction}
         */
        public Direction getDirection() {
            return direction;
        }

        /**
         * 返回当前 {@link Order} 的属性.
         *
         * @return {@link String}
         */
        public String getProperty() {
            return property;
        }

        /**
         * 返回当前 {@link Order} 的属性所对应的排序方向是否为升序
         *
         * @return 升序返回true
         */
        public boolean isAscending() {
            return this.direction.equals(Direction.asc);
        }

        /*
        * (non-Javadoc)
        * @see java.lang.Object#hashCode()
        */
        @Override
        public int hashCode() {

            int result = 17;

            result = 31 * result + direction.hashCode();
            result = 31 * result + property.hashCode();

            return result;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {

            if (this == obj) {
                return true;
            }

            if (!(obj instanceof Order)) {
                return false;
            }

            Order that = (Order) obj;

            return this.direction.equals(that.direction) && this.property.equals(that.property);
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return String.format("{%s: %s}", property, direction);
        }

    }
}
