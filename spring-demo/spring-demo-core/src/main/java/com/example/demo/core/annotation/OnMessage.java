package com.example.demo.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-08-11
 **/
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@Documented
public @interface OnMessage {

    boolean enabled() default true;

}