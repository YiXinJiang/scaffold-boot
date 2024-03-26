package com.jyx.authentication.repeatable;

import com.jyx.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Repeatable 过滤器
 */
@Slf4j
@Component
public class RepeatableFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest
                && StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            ServletRequest requestWrapper = new RepeatedlyRequestWrapper((HttpServletRequest) request, response);
            chain.doFilter(requestWrapper, response);
            return;
        }
        chain.doFilter(request, response);
    }
}

