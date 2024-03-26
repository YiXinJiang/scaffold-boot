package com.jyx.config;

import com.jyx.config.swagger.OpenApiConfig;
import org.springframework.context.annotation.Import;

/**
 * @ClassName: OpenApiConfig
 * @Description: openapi配置导入类
 * @Author: jyx
 * @Date: 2024-03-01 16:06
 **/
@Import({OpenApiConfig.class})
public class ConfigImport {
}
