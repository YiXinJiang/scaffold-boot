package com.jyx.httpclient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.util.Map;

/**
 * @ClassName: RestTemplateApi
 * @Description: RestTemplate服务接口类
 * @Author: pengmingming
 * @Date: 2024-03-04 10:02
 * @Version: 1.0
 **/
public interface RestTemplateService {
    /**
     * get 不带协议头调用
     * @param url 请求URL
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     * */
     <T> ResponseEntity<T> get(String url, Class<T> responseType) throws RestClientException;

    /**
     * get 带协议头调用
     * @param url 请求URL
     * @param headers 请求头参数
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     * */
    <T> ResponseEntity<T> get(String url, Map<String, String> headers, Class<T> responseType) throws RestClientException;

    /**
     * post 不带协议头调用
     * @param url 请求URL
     * @param requestBody 请求消息体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     * */
    <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType) throws RestClientException;

    /**
     * post 带协议头调用
     * @param url 请求URL
     * @param headers 请求头参数
     * @param requestBody 请求消息体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     * */
    <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object requestBody, Class<T> responseType)
            throws RestClientException;

    /**
     * put 不带协议头调用
     * @param url 请求URL
     * @param requestBody 请求消息体
     * @return
     * */
    void put(String url, Object requestBody) throws RestClientException;

    /**
     * put 带协议头调用
     * @param url 请求URL
     * @param headers 请求头参数
     * @param requestBody 请求消息体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     * */
    <T> ResponseEntity<T> put(String url, Map<String, String> headers, Object requestBody, Class<T> responseType)
            throws RestClientException;

    /**
     * delete
     * @param url 请求URL
     * @return
     * */
    void delete(String url) throws RestClientException;

    /**
     * delete 带协议头调用
     * @param url 请求URL
     * @param headers 请求头参数
     * @param requestBody 请求消息体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     * */
     <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Object requestBody, Class<T> responseType)
            throws RestClientException;
}
