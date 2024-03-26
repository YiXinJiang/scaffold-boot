package com.jyx.core.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: Anonymous
 * @Description: 匿名访问不鉴权注解
 * @Author: tgb
 * @Date: 2024-03-04 10:36
 * @Version: 1.0
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anonymous {
}
