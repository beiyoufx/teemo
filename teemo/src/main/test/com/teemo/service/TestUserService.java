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
    public void testGet() {
        User user = userService.get(1L);
        System.out.println(user);
        User user2 = userService.get(1L);
        System.out.println(user2);
    }

    @Test
    public void testGetByEmail() {
        String email = "mayun@alibaba.com";
        User user1 = userService.getByEmail(email);
        System.out.println(user1);
        User user2 = userService.getByEmail(email);
        System.out.println(user2);
    }

    @Test
    public void testGetByMobilePhone() {
        String mobilePhone = "123";
        User user = userService.getByMobilePhone(mobilePhone);

        Assert.assertNotNull(user);
    }

    @Test
    public void testFind() {
        String mobilePhone = "18311113333";
        List<User> users = userService.find("mobilePhone", mobilePhone);
        System.out.println(users);
        List<User> users2 = userService.find("mobilePhone", mobilePhone);
        System.out.println(users2);
    }
}
