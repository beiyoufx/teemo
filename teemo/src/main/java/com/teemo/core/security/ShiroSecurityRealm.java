/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.core.security;

import javax.annotation.Resource;

import com.teemo.core.Constants;
import com.teemo.core.exception.UserBlockedException;
import com.teemo.core.exception.UserNotExistsException;
import com.teemo.core.exception.UserPasswordIncorrectnessException;
import com.teemo.entity.User;
import com.teemo.service.AuthorizationService;
import com.teemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

public class ShiroSecurityRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;
    @Resource
    private AuthorizationService authService;

    public ShiroSecurityRealm() {
        // 这里在UserService和ShiroSecurityRealm方法中进行了密码的匹配，因此不再需要使用shiro的凭证匹配逻辑
        // CustomCredentialsMatcher是一个永真逻辑
        setCredentialsMatcher(new CustomCredentialsMatcher());
    }

    /**
     * 登录时调用
     * @param authcToken 登陆token
     * @return SimpleAuthenticationInfo
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();
        String password = "";
        if (token.getPassword() != null) {
            password = new String(token.getPassword());
        }
        User user;
        try {
            user = userService.login(username, password);
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordIncorrectnessException e) {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (UserBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        }
        setSession(Constants.CURRENT_USER, user);
        return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), this.getName());
    }

    /**
     * 授权时调用
     * @param principals 身份集合
     * @return AuthorizationInfo
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Long userId = (Long) principals.fromRealm(getName()).iterator().next();
        User user = userService.get(userId);
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRoles(authService.findRolesStr(user));
            info.addStringPermissions(authService.findPermissionsStr(user));
            return info;
        } else {
            return null;
        }
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setSession(Object key, Object value){
        Subject subject = SecurityUtils.getSubject();
        if(null != subject){
            Session session = subject.getSession();
            if(null != session){
                session.setAttribute(key, value);
            }
        }
    }

}
