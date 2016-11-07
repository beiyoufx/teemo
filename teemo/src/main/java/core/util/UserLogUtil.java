/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author yongjie.teng
 * @date 16-11-2 下午3:27
 * @email yongjie.teng@foxmail.com
 * @package core.util
 */
public class UserLogUtil {
    private static final Logger USER_LOGGER = LoggerFactory.getLogger("USER_LOG");

    /**
     * <p>记录格式 [ip][用户名][操作][错误消息]<p/>
     * 注意操作如下：
     * loginError 登录失败
     * loginSuccess 登录成功
     * passwordError 密码错误
     * changePassword 修改密码
     * changeStatus 修改状态
     *
     * @param username
     * @param op
     * @param msg
     * @param args
     */
    public static void log(String username, String op, String msg, Object... args) {
        StringBuilder s = new StringBuilder();
        s.append(getBlock(IpUtil.getIp()));
        s.append(getBlock(username));
        s.append(getBlock(op));
        s.append(getBlock(msg));

        USER_LOGGER.info(s.toString(), args);
    }

    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
