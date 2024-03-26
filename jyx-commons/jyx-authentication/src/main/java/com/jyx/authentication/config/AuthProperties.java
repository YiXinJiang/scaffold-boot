package com.jyx.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: AuthProperties
 * @Description: 资源放行
 * @Author: jyx
 * @Date: 2024-03-06 17:51
 **/
@Data
@Component
@ConfigurationProperties(prefix = "web.intercept")
public class AuthProperties {

    private String[] whiteUrl;

    private String[] whiteStatic;

    private String[] openApi;
}
