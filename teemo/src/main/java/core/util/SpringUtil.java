/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

/**
 * @author yongjie.teng
 * @date 16-11-7 下午3:53
 * @email yongjie.teng@foxmail.com
 * @package core.util
 */
public final class SpringUtil implements ApplicationContextAware {

    public static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    /**
     * 根据指定的bean名称获取bean
     * @param beanName
     * @return beanName所对应的实例
     * @throws BeansException
     */
    public static <T> T getBean(String beanName) throws BeansException {
        return (T)ctx.getBean(beanName);
    }

    /**
     * 根据指定的bean名称和指定的类型获取bean
     * @param beanName
     * @param clazz
     * @param <T>
     * @return beanName和<T>所对应的实例
     * @throws BeansException
     */
    public static <T> T getBean(String beanName, Class<T> clazz) throws BeansException {
        return ctx.getBean(beanName, clazz);
    }

    /**
     * 根据指定类型获取bean
     * @param clazz
     * @param <T>
     * @return <T>所对应的实例
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return ctx.getBean(clazz);
    }

    public static String getMessage(String key) throws NoSuchMessageException {
        return ctx.getMessage(key, null, Locale.getDefault());
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     * @param name
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return ctx.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     * @param name
     * @return boolean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return ctx.isSingleton(name);
    }

    /**
     * @param name
     * @return Class 注册对象的类型
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return ctx.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     * @param name
     * @return
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return ctx.getAliases(name);
    }
}