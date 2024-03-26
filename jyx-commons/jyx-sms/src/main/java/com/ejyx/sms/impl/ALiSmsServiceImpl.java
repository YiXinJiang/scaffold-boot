package com.ejyx.sms.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.ejyx.sms.ALiSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ALiSmsServiceImpl
 * @Description: ALiSmsServiceImpl实现类
 * @Author: pengmingming
 * @Date: 2024-03-06 13:47
 * @Version: 1.0
 **/
@Slf4j
@Service
public class ALiSmsServiceImpl implements ALiSmsService {
    private static final String SUCCESS_CODE = "OK";
    private static final String PHONE_NUMBER_REGEX = "^1[3456789]\\d{9}$";
    private static final String PHONE_NUMBER_EXCEPTION = "Invalid phone number";

    private final IAcsClient iAcsClient;

    /**
     * 构造注入
     * @param iAcsClient
     * */
    public ALiSmsServiceImpl(IAcsClient iAcsClient) {
        this.iAcsClient = iAcsClient;
    }

    /**
     * 发送短信
     * @param signName 签名
     * @param phoneNumber 手机号码(中国)
     * @param templateCode 预置短信模板ID
     * @throws Exception
     */
    @Override
    public void send(String signName, String phoneNumber, String templateCode) throws Exception {
        debugOnly("入参: {} | {} | {}", signName, phoneNumber, templateCode);
        checkPhoneNumber(phoneNumber);
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName(signName);
        request.setPhoneNumbers(phoneNumber);
        request.setTemplateCode(templateCode);
        sendSmsRequest(request);
    }

    /**
     * 发送短信
     * @param signName 签名
     * @param phoneNumber 手机号码(中国)
     * @param templateCode 预置短信模板ID
     * @param templateParam 短信模板参数
     * @throws Exception
     */
    @Override
    public void send(String signName, String phoneNumber, String templateCode, String templateParam) throws Exception {
        debugOnly("入参: {} | {} | {} | {}", signName, phoneNumber, templateCode, templateParam);
        checkPhoneNumber(phoneNumber);
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName(signName);
        request.setPhoneNumbers(phoneNumber);
        request.setTemplateCode(templateCode);
        request.setTemplateParam(templateParam);
        sendSmsRequest(request);
    }

    /**
     * 校验手机号码
     * @param phoneNumber 手机号
     */
    private void checkPhoneNumber(String phoneNumber) {
        if (null == phoneNumber || !phoneNumber.matches(PHONE_NUMBER_REGEX)) {
            throw new IllegalArgumentException(PHONE_NUMBER_EXCEPTION);
        }
    }

    private void sendSmsRequest(SendSmsRequest request) throws ClientException {
        SendSmsResponse response = iAcsClient.getAcsResponse(request);
        if (!SUCCESS_CODE.equalsIgnoreCase(response.getCode())) {
            log.error("errCode: {} | errMsg: {}", response.getCode(), response.getMessage());
            throw new RuntimeException(response.getMessage());
        }
        log.info("code: {} | msg: {}", response.getCode(), response.getMessage());
    }

    private void debugOnly(String format, Object... arguments) {
        if (log.isDebugEnabled()) {
            log.debug(format, arguments);
        }
    }
}
