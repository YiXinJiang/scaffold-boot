package com.jyx.cache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: RedisProperties
 * @Description:
 * @Author: jyx
 * @Date: 2024-03-06 09:39
 **/
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    private String host;

    private Integer port;

    private String password;

}
