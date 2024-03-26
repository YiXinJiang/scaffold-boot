package com.jyx.authentication.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyx.authentication.domain.LoginUser;
import com.jyx.authentication.service.TokenService;
import com.jyx.core.base.domain.R;
import com.jyx.core.constants.Constants;
import com.jyx.core.utils.StringUtils;
import com.jyx.system.manager.AsyncFactory;
import com.jyx.system.manager.AsyncManager;
import com.jyx.system.utils.MessageUtils;
import com.jyx.system.utils.ServletUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: LogoutSuccessHandlerImpl
 * @Description: 自定义退出处理类 返回成功
 * @Author: tgb
 * @Date: 2024-03-04 10:35
 * @Version: 1.0
 **/
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Resource
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, MessageUtils.message("user.logout.success")));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ServletUtils.renderString(response, objectMapper.writeValueAsString(R.ok(MessageUtils.message("user.logout.success"))));
    }
}
