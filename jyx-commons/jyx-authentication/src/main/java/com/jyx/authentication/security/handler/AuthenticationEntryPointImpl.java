package com.jyx.authentication.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyx.core.base.domain.R;
import com.jyx.core.constants.HttpStatus;
import com.jyx.core.utils.StringUtils;
import com.jyx.system.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: AuthenticationEntryPointImpl
 * @Description: 认证失败处理类 返回未授权
 * @Author: tgb
 * @Date: 2024-03-04 10:22
 * @Version: 1.0
 **/
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ObjectMapper objectMapper = new ObjectMapper();
        ServletUtils.renderString(response, objectMapper.writeValueAsString(R.fail(code, msg)));
    }
}
