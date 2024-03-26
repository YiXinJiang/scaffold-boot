package com.jyx.authentication.security.filter;

import com.jyx.authentication.domain.LoginUser;
import com.jyx.authentication.service.TokenService;
import com.jyx.authentication.utils.SecurityUtils;
import com.jyx.core.constants.HttpStatus;
import com.jyx.core.constants.UserConstants;
import com.jyx.core.utils.StringUtils;
import com.jyx.system.domain.User;
import com.jyx.system.service.UserService;
import com.jyx.system.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: JwtAuthenticationTokenFilter
 * @Description: token过滤器 验证token有效性
 * @Author: tgb
 * @Date: 2024-03-04 10:36
 * @Version: 1.0
 **/
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication())) {
            Long userId = loginUser.getUserId();
            User user = userService.getById(userId);
            String status = user.getStatus();
            if (StringUtils.isNotNull(status) && !status.equals(UserConstants.NORMAL)) {
                response.sendError(HttpStatus.FORBIDDEN, MessageUtils.message("user.blocked"));
                return;
            }
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
