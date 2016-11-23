/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.core;

/**
 * @author yongjie.teng
 * @date 16-10-28 下午3:24
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.core
 */
public final class Constants {
    /**
     * 系统属性
     * DP_开头的属性全部从DynamicProperty表读取
     */
    public static final String DP_SYSTEM_VERSION = "System.Version"; //用户获取当前系统的版本
    public static final String DP_STATIC_PATH = "Static.Path"; //系统的静态资源地址

    /**
     * 业务属性
     */
    public static final String SESSION_USER = "SESSION_USER";

    /**
     * 操作名称
     */
    public static final String OP_NAME = "op";

    /**
     * 消息key
     */
    public static final String MESSAGE = "message";

    /**
     * 错误key
     */
    public static final String ERROR = "error";

    /**
     * 上个页面地址
     */
    public static final String BACK_URL = "BackURL";
    public static final String IGNORE_BACK_URL = "ignoreBackURL";

    /**
     * 当前请求的地址 带参数
     */
    public static final String CURRENT_URL = "currentURL";

    /**
     * 当前请求的地址 不带参数
     */
    public static final String NO_QUERYSTRING_CURRENT_URL = "noQueryStringCurrentURL";
    public static final String CONTEXT_PATH = "ctx";

    /**
     * 当前登录的用户
     */
    public static final String CURRENT_USER = "CurrentUser";
    public static final String CURRENT_USERNAME = "username";

    /**
     * 各种正则表达式
     */
    public static final String USERNAME_PATTERN = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d_a-zA-Z][\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{1,19}$";
    public static final String MOBILE_PHONE_PATTERN = "^0{0,1}(13[0-9]|15[0-9]|14[0-9]|18[0-9])[0-9]{8}$";
    public static final String EMAIL_PATTERN = "^\\w+([-.]\\w+)*@\\w+([-]\\w+)*\\.(\\w+([-]\\w+)*\\.)*[a-z]{2,3}$";
}
