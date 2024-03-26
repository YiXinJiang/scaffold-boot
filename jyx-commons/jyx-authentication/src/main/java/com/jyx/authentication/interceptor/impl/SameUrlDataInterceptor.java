package com.jyx.authentication.interceptor.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyx.authentication.interceptor.RepeatSubmitInterceptor;
import com.jyx.authentication.repeatable.RepeatedlyRequestWrapper;
import com.jyx.authentication.utils.HttpHelper;
import com.jyx.cache.CacheService;
import com.jyx.core.annotation.RepeatSubmit;
import com.jyx.core.constants.CacheConstants;
import com.jyx.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 判断请求url和数据是否和上一次相同，
 * 如果和上次相同，则是重复提交表单。 有效时间为10秒内。
 */
@Slf4j
@Component
public class SameUrlDataInterceptor extends RepeatSubmitInterceptor {
    public final String REPEAT_PARAMS = "repeatParams";

    public final String REPEAT_TIME = "repeatTime";

    @Value("${token.header}")
    private String header;

    @Autowired
    private CacheService redisCache;

    @SuppressWarnings("unchecked")
    @Override
    public boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation) {
        String requestParams = this.buildRequestParam(request);

        Map<String, Object> nowDataMap = new HashMap<>();
        nowDataMap.put(REPEAT_PARAMS, requestParams);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());

        String url = request.getRequestURI();
        String cacheRepeatKey = this.buildKey(request);
        Object sessionObj = redisCache.get(cacheRepeatKey);
        if (Objects.nonNull(sessionObj)) {
            Map<String, Object> sessionMap = (Map<String, Object>) sessionObj;
            if (sessionMap.containsKey(url)) {
                Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
                if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap, annotation.interval())) {
                    return true;
                }
            }
        }
        Map<String, Object> cacheMap = new HashMap<>();
        cacheMap.put(url, nowDataMap);
        redisCache.set(cacheRepeatKey, cacheMap, (long) annotation.interval(), TimeUnit.MILLISECONDS);
        return false;
    }

    /**
     * 构建请求标识key
     *    指定key + url + 消息头
     * @param request
     * @return
     */
    private String buildKey(HttpServletRequest request) {
        String url = request.getRequestURI();
        // 唯一值（没有消息头则使用请求地址）
        String submitKey = StringUtils.trimToEmpty(request.getHeader(header));
        return CacheConstants.REPEAT_SUBMIT_KEY + url + submitKey;
    }

    /**
     * 构建请求参数
     * @param request
     * @return
     */
    private String buildRequestParam(HttpServletRequest request) {
        String nowParams = "";
        if (request instanceof RepeatedlyRequestWrapper) {
            RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
            nowParams = HttpHelper.getBodyString(repeatedlyRequest);
        }
        // body参数为空，获取Parameter的数据
        if (StringUtils.isEmpty(nowParams)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                nowParams = objectMapper.writeValueAsString(request.getParameterMap());
            } catch (JsonProcessingException e) {
                log.error("exception on resolving request param. param map: {}", request.getParameterMap());
                throw new RuntimeException(e);
            }
        }
        return nowParams;
    }

    /**
     * 判断参数是否相同
     */
    private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
        String nowParams = (String) nowMap.get(REPEAT_PARAMS);
        String preParams = (String) preMap.get(REPEAT_PARAMS);
        return nowParams.equals(preParams);
    }

    /**
     * 判断两次间隔时间
     */
    private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap, int interval) {
        long time1 = (Long) nowMap.get(REPEAT_TIME);
        long time2 = (Long) preMap.get(REPEAT_TIME);
        return (time1 - time2) < interval;
    }
}
