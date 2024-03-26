package com.jyx.authentication.security.context;

import org.springframework.security.core.Authentication;

/**
 * @ClassName: a
 * @Description: 身份验证信息
 * @Author: tgb
 * @Date: 2024-03-04 14:23
 * @Version: 1.0
 **/
public class AuthenticationContextHolder {
    private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    public static Authentication getContext() {
        return contextHolder.get();
    }

    public static void setContext(Authentication context) {
        contextHolder.set(context);
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
