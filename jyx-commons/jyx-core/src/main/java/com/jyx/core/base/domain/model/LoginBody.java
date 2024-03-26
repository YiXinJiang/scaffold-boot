package com.jyx.core.base.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: LoginBody
 * @Description: 用户登陆类
 * @Author: tgb
 * @Date: 2024-03-04 11:53
 * @Version: 1.0
 **/
@Data
public class LoginBody implements Serializable {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;
}
