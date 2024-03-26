package com.jyx.authentication.service;

import com.jyx.authentication.domain.LoginUser;
import com.jyx.core.exception.ServiceException;
import com.jyx.core.utils.StringUtils;
import com.jyx.system.enums.UserStatus;
import com.jyx.system.service.UserService;
import com.jyx.system.serviceImpl.SysPermissionService;
import com.jyx.system.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ServiceDetailsServiceImpl
 * @Description: 加载用户实现类
 * @Author: tgb
 * @Date: 2024-03-04 10:18
 * @Version: 1.0
 **/
@Service
public class ServiceDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(ServiceDetailsServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("user.not.exists");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException("user.password.delete");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("user.blocked");
        }

        passwordService.validate(user);

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(UserVo user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId())
                .setUser(user)
                .setPermissions(permissionService.getMenuPermission(user));
        return loginUser;
    }
}
