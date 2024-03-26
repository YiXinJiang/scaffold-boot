package com.jyx.authentication.service;

import com.jyx.authentication.security.context.AuthenticationContextHolder;
import com.jyx.authentication.utils.SecurityUtils;
import com.jyx.cache.CacheService;
import com.jyx.core.constants.CacheConstants;
import com.jyx.core.exception.user.UserPasswordNotMatchException;
import com.jyx.core.exception.user.UserPasswordRetryLimitExceedException;
import com.jyx.system.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: SysPasswordService
 * @Description: 登陆密码校验
 * @Author: tgb
 * @Date: 2024-03-04 14:21
 * @Version: 1.0
 **/
@Service
public class SysPasswordService {

    @Resource
    private CacheService cacheService;

    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;

    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username) {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void validate(User user) {
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();

        Long retryCount = cacheService.get(getCacheKey(username));

        if (retryCount == null) {
            retryCount = 0L;
        }

        if (retryCount >= maxRetryCount) {
            throw new UserPasswordRetryLimitExceedException(maxRetryCount, (long) lockTime);
        }

        if (!matches(user, password)) {
            retryCount = retryCount + 1;
            cacheService.set(getCacheKey(username), retryCount, (long) lockTime, TimeUnit.MINUTES);
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(User user, String rawPassword) {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String loginName) {
        if (cacheService.exist(getCacheKey(loginName))) {
            cacheService.remove(getCacheKey(loginName));
        }
    }
}
