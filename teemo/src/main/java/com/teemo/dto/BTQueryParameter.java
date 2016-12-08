package com.teemo.dto;

import java.io.Serializable;

/**
 * Bootstrap Table 表格视图请求参数
 * @author yongjie.teng
 * @date 16-12-7 下午3:32
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.dto
 */
public class BTQueryParameter implements Serializable {
    private static final long serialVersionUID = 639145165702621352L;
    private String searchText;
    private Integer pageSize;
    private Integer pageNumber;
    private String sortName;
    private String sortOrder;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * Bootstrap Table的分页是从1开始的
     * 这里需要做兼容，将首页下标改为0
     * @param pageNumber
     */
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber - 1;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
