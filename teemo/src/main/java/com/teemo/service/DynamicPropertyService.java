/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.service;

import com.teemo.dao.DynamicPropertyDao;
import com.teemo.entity.DynamicProperty;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yongjie.teng
 * @date 16-11-8 下午5:32
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.service
 */
@Service
public class DynamicPropertyService extends BaseService<DynamicProperty> {
    private DynamicPropertyDao dynamicPropertyDao;
    @Resource
    public void setDynamicPropertyDao(DynamicPropertyDao dynamicPropertyDao) {
        this.dynamicPropertyDao = dynamicPropertyDao;
        this.dao = dynamicPropertyDao;
    }
}
