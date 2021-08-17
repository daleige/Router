package com.chenyangqi.router.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface ServiceLoader {
    /**
     * 实现的接口（或继承的父类）
     */
    Class<?>[] interfaces();

    /**
     * 同一个接口的多个实现类之间，可以通过唯一的key区分。
     */
    String key() default "";

    /**
     * 是否为单例。如果是单例，则使用ServiceLoader.getService不会重复创建实例。
     */
    boolean singleton() default false;
}
