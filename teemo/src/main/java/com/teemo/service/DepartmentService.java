/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.service;

import com.teemo.dao.DepartmentDao;
import com.teemo.entity.Department;
import core.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yongjie.teng
 * @date 16-12-15 下午8:00
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.service
 */
@Service
public class DepartmentService extends BaseService<Department> {
    private DepartmentDao departmentDao;
    @Resource
    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
        this.dao = departmentDao;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public boolean logicDelete(Long id) {
        boolean result = false;
        if (id != null) {
            Department department = get(id);
            if (department != null && Boolean.FALSE.equals(department.getDeleted())) {
                department.setDeleted(Boolean.TRUE);
                update(department);
                result = true;
            }
        }
        return result;
    }
}
