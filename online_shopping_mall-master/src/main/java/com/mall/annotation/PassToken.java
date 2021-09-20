package com.mall.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 跳过token验证的注解
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/26 18:19
 */
@Target({ElementType.METHOD, ElementType.TYPE}) // 可用于方法和类
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {
    boolean required() default true;
}
