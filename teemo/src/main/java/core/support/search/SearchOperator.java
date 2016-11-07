/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.support.search;

import core.exception.SearchException;
import core.util.StringUtil;

import java.util.Arrays;

/**
 * <p>查询操作符</p>
 * @author yongjie.teng
 * @date 16-11-3 下午2:41
 * @email yongjie.teng@foxmail.com
 * @package core.support.search
 */
public enum SearchOperator {
    eq("等于", "="), ne("不等于", "!="),
    gt("大于", ">"), gte("大于等于", ">="), lt("小于", "<"), lte("小于等于", "<="),
    prefixLike("前缀模糊匹配", "like"), prefixNotLike("前缀模糊不匹配", "not like"),
    suffixLike("后缀模糊匹配", "like"), suffixNotLike("后缀模糊不匹配", "not like"),
    like("模糊匹配", "like"), notLike("不匹配", "not like"),
    isNull("空", "is null"), isNotNull("非空", "is not null"),
    in("包含", "in"), notIn("不包含", "not in"), custom("自定义默认的", null);

    private final String info;
    private final String symbol;

    SearchOperator(final String info, String symbol) {
        this.info = info;
        this.symbol = symbol;
    }

    public String getInfo() {
        return info;
    }

    public String getSymbol() {
        return symbol;
    }

    public static String toStringAllOperator() {
        return Arrays.toString(SearchOperator.values());
    }

    /**
     * 是否为一元操作
     * 一元操作符只需要一个参数
     * @param operator
     * @return 如果操作符是"is not null"或者"is null"则返回true否则false
     */
    public static boolean isAllowBlankValue(final SearchOperator operator) {
        return operator == SearchOperator.isNotNull || operator == SearchOperator.isNull;
    }

    /**
     * 是否允许多值操作
     * in操作符允许接收多个值
     * @param operator
     * @return 如果操作符是"is not null"或者"is null"则返回true否则false
     */
    public static boolean isAllowMultiValue(final SearchOperator operator) {
        return operator == SearchOperator.in;
    }

    public static SearchOperator valueBySymbol(String symbol) throws SearchException {
        symbol = formatSymbol(symbol);
        for (SearchOperator operator : values()) {
            if (operator.getSymbol().equals(symbol)) {
                return operator;
            }
        }
        throw new SearchException("SearchOperator not match search operator symbol : " + symbol);
    }

    private static String formatSymbol(String symbol) {
        if (StringUtil.isEmpty(symbol)) {
            return symbol;
        }
        return symbol.trim().toLowerCase().replace("  ", " ");
    }
}