package com.ejyx.sms.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.HttpClientConfig;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: ALiSmsConfig
 * @Description: 阿里sms客户配置
 * @Author: pengmingming
 * @Date: 2024-03-06 13:48
 * @Version: 1.0
 **/
@Configuration
public class ALiSmsConfig {
    @Value(value ="${spring.sms.ali.regionId:default}")
    private String regionId;
    @Value(value ="${spring.sms.ali.keyId}")
    private String accessKeyId;
    @Value(value ="${spring.sms.ali.secret}")
    private String accessKeySecret;
    @Value(value ="${spring.sms.ali.connectTimeout:3000}")
    private int connectTimeout;
    @Value(value ="${spring.sms.ali.readTimeout:5000}")
    private int readTimeout;

    /**
     * IAcsClient ben缺失时创建
     * @return IAcsClient
     * */
    @Bean(destroyMethod="shutdown")
    @ConditionalOnMissingBean(IAcsClient.class)
    public IAcsClient iAcsClient() {
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        HttpClientConfig clientConfig = profile.getHttpClientConfig();
        clientConfig.setReadTimeoutMillis(readTimeout);
        clientConfig.setProtocolType(ProtocolType.HTTPS);
        clientConfig.setConnectionTimeoutMillis(connectTimeout);
        return new DefaultAcsClient(profile);
    }
}
