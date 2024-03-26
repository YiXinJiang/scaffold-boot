package com.jyx.oss.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: ALiOssConfig
 * @Description: 阿里Oss客户配置
 * @Author: pengmingming
 * @Date: 2024-03-05 13:48
 * @Version: 1.0
 **/
@Configuration
public class ALiOssConfig {
    @Value(value ="${spring.oss.ali.endpoint:oss-cn-beijing.aliyuncs.com}")
    private String endpoint;
    @Value(value ="${spring.oss.ali.keyId}")
    private String accessKeyId;
    @Value(value ="${spring.oss.ali.secret}")
    private String accessKeySecret;

    @Value(value ="${spring.oss.ali.connectTimeout:3000}")
    private int connectTimeout;
    @Value(value ="${spring.oss.ali.readTimeout:5000}")
    private int readTimeout;

    /**
     * OSS ben缺失时创建
     * @return OSS
     * */
    @Bean(destroyMethod="shutdown")
    @ConditionalOnMissingBean(OSS.class)
    public OSS oss() {
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setConnectionTimeout(connectTimeout);
        conf.setSocketTimeout(readTimeout);
        conf.setProtocol(Protocol.HTTPS);
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);
    }
}
