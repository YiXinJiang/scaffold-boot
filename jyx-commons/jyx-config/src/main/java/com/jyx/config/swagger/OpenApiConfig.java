package com.jyx.config.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName: OpenApiConfig
 * @Description: openapi配置 原swagger
 * @Author: jyx
 * @Date: 2024-03-01 15:37
 **/
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("裕汇泓")
                        // 接口文档描述
                        .description("后端http接口文档")
                        // 接口文档版本
                        .version("v1.0")
                        // 开发者联系方式
                        .contact(new Contact().name("郑龙胜")))
                .externalDocs(new ExternalDocumentation()
                        // 额外补充说明
                        .description("用于充电桩项目")
                        // 额外补充链接
                        .url("http://192.168.1.95:60081/root/backend-basic-template.git"));
    }

}

