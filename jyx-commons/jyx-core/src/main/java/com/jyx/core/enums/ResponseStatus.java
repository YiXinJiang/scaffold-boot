package com.jyx.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @EnumName: ResponseStatus
 * @Description: 系统响应码，用于返回至客户端的异常信息
 * @Author: jyx
 * @Date: 2024-03-07 17:15
 **/
@AllArgsConstructor
public enum ResponseStatus {

    RUNTIME_EXCEPTION(40001, Series.SERVER_ERROR, "系统运行时异常"),
    UNKNOWN_EXCEPTION(40002, Series.SERVER_ERROR, "系统未知异常"),

    MISSING_PATH_VARIABLE(41001, Series.CLIENT_ERROR, "请求路径中缺少必需的路径变量"),
    PARAM_TYPE_NOT_MATCH(41002, Series.CLIENT_ERROR, "请求路径中缺少必需的路径变量"),

    BUSINESS_EXCEPTION(42000, Series.BUSINESS_ERROR, "未知的业务异常"),
    CAPTCHA_EXPIRE(42001, Series.BUSINESS_ERROR, "验证码过期"),
    CAPTCHA_NOT_MATCH(42002, Series.BUSINESS_ERROR, "验证码不匹配"),
    USER_PASSWORD_NOT_MATCH(42003, Series.BUSINESS_ERROR, "用户名密码不匹配"),

    ;

    private final int value;

    @Getter
    private final Series series;

    @Getter
    private final String reasonPhrase;

    public int value() {
        return this.value;
    }

    /**
     * 异常归类
     */
    @AllArgsConstructor
    public enum Series {
        /**
         * 业务异常
         */
        BUSINESS_ERROR(1),
        /**
         * 客户端请求异常
         */
        CLIENT_ERROR(2),
        /**
         * 服务器处理异常
         */
        SERVER_ERROR(3);

        private final int value;

        public int value() {
            return this.value;
        }
    }

}
