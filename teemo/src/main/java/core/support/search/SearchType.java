/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.support.search;

/**
 * WHERE过滤条件连接类型
 * @author yongjie.teng
 * @date 16-11-3 下午4:45
 * @email yongjie.teng@foxmail.com
 * @package core.support.search
 */
public enum SearchType {
    OR("OR连接", "or"), AND("AND连接","and");

    private final String info;
    private final String type;

    SearchType(final String info, String type) {
        this.info = info;
        this.type = type;
    }

    public String getInfo() {
        return this.info;
    }

    public String getTypeStr() {
        return this.type;
    }
}
