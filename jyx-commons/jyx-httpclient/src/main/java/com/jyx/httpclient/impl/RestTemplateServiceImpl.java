package com.jyx.httpclient.impl;

import com.jyx.httpclient.RestTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

/**
 * @ClassName: RestTemplateServiceImpl
 * @Description: RestTemplate接口实现类
 * @Author: pengmingming
 * @Date: 2024-03-04 10:05
 * @Version: 1.0
 **/
@Slf4j
@Service
public class RestTemplateServiceImpl implements RestTemplateService {
    private final RestTemplate restTemplate;

    /**
     * 构造注入
     * @param restTemplate
     * */
    public RestTemplateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * get 不带协议头调用
     * @param url
     * @return
     * */
    @Override
    public <T> ResponseEntity<T> get(String url, Class<T> responseType) throws RestClientException  {
        debugOnly("入参：{} | {}", url, responseType.getTypeName());
        return restTemplate.getForEntity(url, responseType);
    }

    /**
     * get 带协议头调用
     * @param url
     * @param headers
     * @return
     * */
    @Override
    public <T> ResponseEntity<T> get(String url, Map<String, String> headers, Class<T> responseType)
            throws RestClientException {
        debugOnly("入参：{} | {} | {}", url, headers, responseType.getTypeName());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
    }

    /**
     * post 不带协议头调用
     * @param url 请求URL
     * @param requestBody 请求消息体
     * @return ResponseEntity 响应对象封装类
     * */
    @Override
    public <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType)
            throws RestClientException {
        debugOnly("入参：{} | {} | {}", url, requestBody, responseType.getTypeName());
        return restTemplate.postForEntity(url, requestBody, responseType);
    }

    /**
     * post 带协议头调用
     * @param url 请求URL
     * @param headers 请求头参数
     * @param requestBody 请求消息体
     * @return ResponseEntity 响应对象封装类
     * */
    @Override
    public <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object requestBody, Class<T> responseType)
            throws RestClientException {
        debugOnly("入参：{} | {} | {} | {}", url, headers, requestBody, responseType.getTypeName());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
    }

    /**
     * put 不带协议头调用
     * @param url 请求URL
     * @param requestBody 请求消息体
     * @return
     * */
    @Override
    public void put(String url, Object requestBody) throws RestClientException {
        debugOnly("put 入参：{} | {}", url, requestBody);
        restTemplate.put(URI.create(url), requestBody);
    }

    /**
     * put 带协议头调用
     * @param url 请求URL
     * @param headers 请求头参数
     * @param requestBody 请求消息体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     * */
    public  <T> ResponseEntity<T> put(String url, Map<String, String> headers, Object requestBody, Class<T> responseType)
            throws RestClientException {
        debugOnly("入参：{} | {} | {} | {}", url, headers, requestBody, responseType.getTypeName());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType);
    }

    /**
     * delete
     * @param url 请求URL
     * @return
     * */
    @Override
    public void delete(String url) throws RestClientException {
        debugOnly("入参：{}}", url);
        restTemplate.delete(URI.create(url));
    }

    /**
     * delete 带协议头调用
     * @param url 请求URL
     * @param headers 请求头参数
     * @param requestBody 请求消息体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     * */
    public <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Object requestBody, Class<T> responseType)
            throws RestClientException {
        debugOnly("入参：{} | {} | {} | {}", url, headers, requestBody, responseType.getTypeName());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, responseType);
    }

    private void debugOnly(String format, Object... arguments) {
        if (log.isDebugEnabled()) {
            log.debug(format, arguments);
        }
    }
}
