package com.ejyx.sms;

/**
 * @InterfaceName: ALiSmsService
 * @Description: 阿里sms服务接口类
 * @Author: pengmingming
 * @Date: 2024-03-06 13:44
 * @Version: 1.0
 **/
public interface ALiSmsService {
    /**
     * 发送短信
     * @param signName 签名
     * @param phoneNumber 手机号码(中国)
     * @param templateCode 预置短信模板ID
     * @throws Exception
     */
    void send(String signName, String phoneNumber, String templateCode) throws Exception;

    /**
     * 发送短信
     * @param signName 签名
     * @param phoneNumber 手机号码(中国)
     * @param templateCode 预置短信模板ID
     * @param templateParam 短信模板参数
     * @throws Exception
     */
    void send(String signName, String phoneNumber, String templateCode, String templateParam) throws Exception;
}
