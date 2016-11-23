package com.teemo.core.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author yongjie.teng
 * @date 16-11-21 下午9:01
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.core.security
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        /**
         * 由于在UserService中已经实现了密码校验功能，因此在这里放一个空实现
         * 或者你也可以有一个自己的实现
         */
        return true;
    }
}
