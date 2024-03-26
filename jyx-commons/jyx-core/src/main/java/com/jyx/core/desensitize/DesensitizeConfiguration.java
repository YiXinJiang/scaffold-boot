package com.jyx.core.desensitize;

import com.jyx.core.utils.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.*;

/**
 * @ClassName: DesensitizeConfiguration
 * @Description: 日志初始化脱敏配置
 * @Author: jyx
 * @Date: 2024-03-15 10:27
 **/
public class DesensitizeConfiguration {

    private static final String PROFILE_ENABLE = "desensitize.enable.";
    protected static final String LOG_ENABLE = PROFILE_ENABLE + "log-enable";
    protected static final String API_ENABLE = PROFILE_ENABLE + "api-enable";

    protected static final String PROFILE_PREFIX = LOG_ENABLE + "desensitize.property.";
    protected static final String ID_CARD = PROFILE_PREFIX + "id-card";
    protected static final String USER_NAME = PROFILE_PREFIX + "username";
    protected static final String PHONE = PROFILE_PREFIX + "phone";
    protected static final String ADDRESS = PROFILE_PREFIX + "address";
    public static Properties properties;

    public static Map<String, SensitivityEnum> regularMap = new HashMap<>();
    private static boolean logEnable;
    private static boolean apiEnable;

    static {
        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        yamlFactory.setResources(new ClassPathResource("application-core.yml"));
        properties = yamlFactory.getObject();
        if (Objects.isNull(properties)) {
            logEnable = false;
            apiEnable = false;
        } else {
            logEnable = (boolean) properties.get(LOG_ENABLE);
            apiEnable = (boolean) properties.get(API_ENABLE);
        }

        if (Objects.nonNull(properties)) {
            Optional.ofNullable(properties.get(ID_CARD)).ifPresent(value -> {
                if (StringUtils.isNotBlank(value + "")) {
                    regularMap.put(value + "", SensitivityEnum.ID_CARD);
                }
            });
            Optional.ofNullable(properties.get(USER_NAME)).ifPresent(value -> {
                if (StringUtils.isNotBlank(value + "")) {
                    regularMap.put(value + "", SensitivityEnum.USERNAME);
                }
            });
            Optional.ofNullable(properties.get(PHONE)).ifPresent(value -> {
                if (StringUtils.isNotBlank(value + "")) {
                    regularMap.put(value + "", SensitivityEnum.PHONE);
                }
            });
            Optional.ofNullable(properties.get(ADDRESS)).ifPresent(value -> {
                if (StringUtils.isNotBlank(value + "")) {
                    regularMap.put(value + "", SensitivityEnum.ADDRESS);
                }
            });
        }
    }

    public static boolean logIsEnable() {
        return logEnable;
    }

    public static boolean apiIsEnable() {
        return apiEnable;
    }

    public static void logDisable() {
        logEnable = false;
    }

    public static void apiDisable() {
        apiEnable = false;
    }

    public static void logEnable() {
        logEnable = true;
    }

    public static void apiEnable() {
        apiEnable = true;
    }

}
