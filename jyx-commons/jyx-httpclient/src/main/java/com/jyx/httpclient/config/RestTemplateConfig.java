package com.jyx.httpclient.config;

import com.jyx.httpclient.utils.CustomClientRequestFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: RestTemplateConfig
 * @Description: RestTemplate配置类
 * @Author: pengmingming
 * @Date: 2024-03-04 10:29
 * @Version: 1.0
 **/
@Configuration
public class RestTemplateConfig {
    @Value(value ="${spring.http.client.connectTimeout:3000}")
    private int connectTimeout;
    @Value(value ="${spring.http.client.readTimeout:5000}")
    private int readTimeout;

    /**
     * restTemplate ben缺失时创建
     * @return restTemplate
     * */
    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new CustomClientRequestFactory();
        // 设置连接超时时间为3秒
        requestFactory.setConnectTimeout(connectTimeout);
        // 设置读取超时时间为5秒
        requestFactory.setReadTimeout(readTimeout);
        return new RestTemplate(requestFactory);
    }
}
