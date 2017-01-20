package com.teemo.core.aop;

import com.teemo.entity.Menu;
import com.teemo.entity.Role;
import core.cache.BaseCacheAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 菜单切面缓存实现
 * 这里应用层面的缓存只是作为一个简单实现，目的是给出一个应用层缓存的sample，
 * 实际上在Hibernate的查询缓存中已经对SQL的查询结果做了缓存，
 * 此处的菜单即使不缓存，也仅仅只是多了一个组装成菜单结构的过程而已。
 * 不会将查询操作穿透到数据库
 * @author yongjie.teng
 * @date 17-1-16 上午10:04
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.core.aop
 */
@Component
@Aspect
public class MenuCacheAspect extends BaseCacheAspect {

    private String menusPrefix = "menus";

    public MenuCacheAspect() {
        setCacheName("sys-menus");
    }

    /**
     * 匹配资源Service
     */
    @Pointcut(value = "target(com.teemo.service.ResourceService)")
    private void resourceServiceServicePointcut() {
    }

    /**
     * 匹配角色Service
     */
    @Pointcut(value = "target(com.teemo.service.RoleService)")
    private void roleServiceServicePointcut() {
    }

    /**
     * 清除菜单缓存切点
     * 前置条件为资源被更新
     */
    @Pointcut(value = "execution(* delete(*)) " +
                    "|| execution(* deleteById(..)) " +
                    "|| execution(* deleteResource(*)) " +
                    "|| execution(* update(..)) " +
                    "|| execution(* save(*)) " +
                    "|| execution(* merge(*)) " +
                    "|| execution(* persist(*))")
    private void resourceCacheableClearPointcut() {
    }

    /**
     * 清除菜单缓存切点
     * 前置条件是角色被更新
     */
    @Pointcut(value = "execution(* delete(*)) " +
                    "|| execution(* deleteById(..)) " +
                    "|| execution(* auth(..)) " +
                    "|| execution(* deleteRole(*)) " +
                    "|| execution(* update(..)) " +
                    "|| execution(* save(*)) " +
                    "|| execution(* merge(*)) " +
                    "|| execution(* persist(*))")
    private void roleCacheableClearPointcut() {
    }

    /**
     * 需要缓存的数据的切点
     * 业务层不缓存“根据主键获取实体”
     */
    @Pointcut(value = "execution(* findMenu(*)) && args(arg)", argNames = "arg")
    private void cacheablePointcut(Set<Role> arg) {
    }

    /**
     * 使用环绕通知来处理缓存查询逻辑
     * @param pjp ProceedingJoinPoint
     * @return Object
     * @throws Throwable
     */
    @Around(value = "resourceServiceServicePointcut() && cacheablePointcut(arg)", argNames = "pjp, arg")
    public Object cacheableAdvice(ProceedingJoinPoint pjp, Set<Role> arg) throws Throwable {
        String key = menusKey(arg);
        List<Menu> menus = get(key);

        //cache hit
        if (menus != null) {
            log.debug("cacheName:{}, hit key:{}", cacheName, key);
            return menus;
        }
        log.debug("cacheName:{}, miss key:{}", cacheName, key);

        //cache miss
        menus = (List<Menu>) pjp.proceed();

        //put cache
        put(key, menus);
        return menus;
    }

    /**
     * 使用后置通知更新缓存或者清除缓存
     */
    @After(value = "(resourceServiceServicePointcut() && resourceCacheableClearPointcut()) || (roleServiceServicePointcut() && roleCacheableClearPointcut())")
    public void afterAdvice() {
        clear();
    }

    private String menusKey(Set<Role> roles) {
        String key = menusPrefix;
        if (roles != null) {
            for (Role role : roles) {
                if (role != null) {
                    key += "-" + role.getRoleKey();
                }
            }
        }
        return key;
    }
}
