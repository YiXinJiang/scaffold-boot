package com.jyx.core.desensitize;

import lombok.AllArgsConstructor;

import java.util.function.Function;

/**
 * @EnumName: SensitivityEnum
 * @Description:
 * @Author: jyx
 * @Date: 2024-03-14 16:44
 **/
@AllArgsConstructor
public enum SensitivityEnum {

    /**
     * 姓名
     */
    USERNAME(s -> s.replaceAll("\\S*(\\S)", "***$1"), "[\\u4e00-\\u9fa5_a-zA-Z0-9-]{2,50}"),
    /**
     * 身份证
     */
    ID_CARD(s -> s.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1****$2"), "(\\d{17}[0-9Xx]|\\d{14}[0-9Xx])"),
    /**
     * 手机号
     */
    PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"), "(?<!\\d)(?:(?:1[3456789]\\d{9})|(?:861[356789]\\d{9}))(?!\\d)"),
    /**
     * 地址
     */
    ADDRESS(s -> s.replaceAll("(\\S{3})\\S{2}(\\S*)\\S{2}", "$1****$2****"), "[\\u4e00-\\u9fa5_a-zA-Z0-9-]{3,200}")

    ;

    /**
     * 脱敏器
     */
    private final Function<String, String> desensitizer;

    /**
     * 从字符串中发现脱敏字段的正则
     */
    private final String logFinder;

    public String logFinder(){
        return this.logFinder;
    }

    public Function<String, String> desensitizer() {
        return this.desensitizer;
    }

}
