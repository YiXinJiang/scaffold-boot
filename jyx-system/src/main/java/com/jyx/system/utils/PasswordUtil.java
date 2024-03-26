package com.jyx.system.utils;

import com.jyx.core.base.domain.model.LoginBody;
import com.jyx.core.utils.RSAUtil;

/**
 * @ClassName: PasswordUtil
 * @Description: 密码处理
 * @Author: tgb
 * @Date: 2024-03-07 15:05
 * @Version: 1.0
 **/
public class PasswordUtil {

    /**
     * 密码RSA解密
     *
     * @param loginBody
     * @author tgb
     * @date 2024/3/7 15:11
     */
    public static void passwordDecrypt(LoginBody loginBody) {
        String password = loginBody.getPassword();
        String decryptPassword = RSAUtil.decryptByPrivateKey(password);
        loginBody.setPassword(decryptPassword);
    }
}
