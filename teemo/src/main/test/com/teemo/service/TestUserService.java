package com.teemo.service;

import com.teemo.entity.User;
import core.util.SpringUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author yongjie.teng
 * @date 16-11-7 下午3:42
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.service
 */
public class TestUserService {
    private static UserService userService;
    @BeforeClass
    public static void before() {
        SpringUtil.ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = SpringUtil.getBean("userService");
    }

    @Test
    public void testGetByEmail() {
        String email = "mayun@alibaba.com";
        User user = userService.getByEmail(email);

        Assert.assertNotNull(user);
    }

    @Test
    public void testGetByMobilePhone() {
        String mobilePhone = "123";
        User user = userService.getByMobilePhone(mobilePhone);

        Assert.assertNotNull(user);
    }

    @Test
    public void testFind() {
        String mobilePhone = "123";
        List<User> users = userService.find("mobilePhone", mobilePhone);

        Assert.assertEquals(2, users.size());
    }
}
