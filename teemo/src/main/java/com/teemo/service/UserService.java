/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.service;

import com.teemo.dao.UserDao;
import com.teemo.entity.User;
import core.service.BaseService;
import core.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

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

    public User getByEmail(String email) {
        if (StringUtil.isEmpty(email)) {
            return null;
        }
        return get("email", email);
    }

    public User getByMobilePhone(String mobilePhone) {
        if (StringUtil.isEmpty(mobilePhone)) {
            return null;
        }
        return get("mobilePhone", mobilePhone);
    }

    private boolean isMatched(User user, String password) {
        return user.getPassword().equals(encryptPassword(user.getUsername(), password, user.getSalt()));
    }

    private String encryptPassword(String username, String password, String salt) {
        return DigestUtils.md5Hex(username + password + salt);
    }
}
