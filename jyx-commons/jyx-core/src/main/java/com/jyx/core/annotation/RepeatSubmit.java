package com.jyx.core.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: RepeatSubmit
 * @Description: 自定义注解防止表单重复提交
 * @Author: tgb
 * @Date: 2024-03-04 10:36
 * @Version: 1.0
 **/
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {
    /**
     * 间隔时间(ms)，小于此时间视为重复提交
     */
    int interval() default 5000;

    /**
     * 提示消息
     */
    String message() default "不允许重复提交，请稍候再试";
}
