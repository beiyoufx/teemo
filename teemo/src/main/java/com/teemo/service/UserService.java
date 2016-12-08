/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.service;

import com.teemo.core.Constants;
import com.teemo.core.exception.UserBlockedException;
import com.teemo.core.exception.UserNotExistsException;
import com.teemo.core.exception.UserPasswordIncorrectnessException;
import com.teemo.dao.UserDao;
import com.teemo.entity.User;
import com.teemo.entity.UserStatus;
import core.service.BaseService;
import core.util.StringUtil;
import com.teemo.core.util.UserLogUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;

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

    public User register(User user) {
        String salt = randomSalt();
        user.setSalt(salt);
        String password = encryptPassword(user.getUsername(), user.getPassword(), salt);
        user.setPassword(password);
        Long id = (Long) save(user);
        user.setId(id);
        return user;
    }

    public User login(String username, String password) throws UserNotExistsException, UserPasswordIncorrectnessException, UserBlockedException {
        if (!StringUtil.isNotEmpty(username, password)) {
            UserLogUtil.log(username,"LoginError", "username is empty.");
            throw new UserNotExistsException();
        }

        User user = getByUsername(username);
        if (user == null && username.matches(Constants.EMAIL_PATTERN)) {
            user = getByEmail(username);
        }

        if (user == null && username.matches(Constants.MOBILE_PHONE_PATTERN)) {
            user = getByMobilePhone(username);
        }

        if (user == null || Boolean.TRUE.equals(user.getDeleted()) ) {
            UserLogUtil.log(username,"LoginError", "user is not exists.");
            throw new UserNotExistsException();
        }

        if (!isMatched(user, password)) {
            UserLogUtil.log(username,"LoginError", "password is not correct.");
            throw new UserPasswordIncorrectnessException();
        }

        if (UserStatus.blocked == user.getStatus()) {
            UserLogUtil.log(username,"LoginError", "user is blocked.");
            throw new UserBlockedException();
        }

        UserLogUtil.log(user.getUsername(),"LoginSuccess", "login success.");
        return user;
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

    public User getByUsername(String username) {
        if (StringUtil.isEmpty(username)) {
            return null;
        }
        return get("username", username);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public boolean logicDelete(Serializable id) {
        boolean result = false;
        if (id != null) {
            User user = get(id);
            if (user != null && Boolean.FALSE.equals(user.getDeleted())) {
                user.setDeleted(Boolean.TRUE);
                update(user);
                result = true;
            }
        }
        return result;
    }

    private String randomSalt() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    private boolean isMatched(User user, String password) {
        if (user.getPassword().equals(encryptPassword(user.getUsername(), password, user.getSalt()))) {
            return true;
        }
        return false;
    }

    private String encryptPassword(String username, String password, String salt) {
        return DigestUtils.md5Hex(username + password + salt);
    }
}
