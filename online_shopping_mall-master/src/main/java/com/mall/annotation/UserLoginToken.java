package com.mall.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要登录才能进行操作的注解
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/26 18:16
 */
@Target({ElementType.METHOD, ElementType.TYPE}) // 可用于方法和类
@Retention(RetentionPolicy.RUNTIME)
public @interface UserLoginToken {
    boolean required() default true;
}
