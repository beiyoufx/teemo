package com.teemo.service;

import com.teemo.dao.UserDao;
import com.teemo.entity.User;
import core.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yongjie.teng
 * @version 1.0
 * @date 16-10-27
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.service
 * @project teemo
 */
@Service
public class UserService extends BaseService<User> {
    private UserDao userDao;
    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
        this.dao = userDao;
    }
}
