/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.service;

import com.teemo.dao.DynamicPropertyDao;
import com.teemo.entity.DynamicProperty;
import core.service.BaseService;
import core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 更新属性字段，key保持不变，version+0.1
     * @param dynamicProperty 新版本的动态属性
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void updateVersion(DynamicProperty dynamicProperty) {
        if (dynamicProperty.getId() != null) {
            dynamicPropertyDao.mergeDynamicProperty(dynamicProperty);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public boolean logicDelete(Long id) {
        boolean result = false;
        if (id != null) {
            DynamicProperty dynamicProperty = get(id);
            if (dynamicProperty != null && Boolean.FALSE.equals(dynamicProperty.getDeleted())) {
                dynamicProperty.setDeleted(Boolean.TRUE);
                update(dynamicProperty);
                result = true;
            }
        }
        return result;
    }

    /**
     * 根据动态属性的Key获取实体
     * @param propertyKey 动态属性Key
     * @return 实体
     */
    public DynamicProperty getByPropertyKey(String propertyKey) {
        if (StringUtil.isEmpty(propertyKey)) {
            return null;
        }
        return get("dynamicPropertyKey", propertyKey);
    }
}
