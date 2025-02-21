package com.jyx.core.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyx.core.enums.DateFormat;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @ClassName: JacksonConfig
 * @Description: jackson序列化配置 response中的json数据将会遵循此配置
 * @Author: jyx
 * @Date: 2024-03-13 15:43
 **/
@Configuration
public class JacksonConfig {

    @Primary
    @Bean("objectMapper")
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 日期格式
        mapper.setDateFormat(new SimpleDateFormat(DateFormat.DEFAULT_DATE_TIME_FORMAT.value()));
        //map.put("CTT", "Asia/Shanghai");
        mapper.setTimeZone(TimeZone.getTimeZone(DateFormat.TimeZone.GREENWICH_MEAN_TIME.value()));
        // Include.NON_NULL 属性为NULL 不序列化
        // ALWAYS // 默认策略，任何情况都执行序列化
        // NON_EMPTY // null、集合数组等没有内容、空字符串等，都不会被序列化
        // NON_DEFAULT // 如果字段是默认值，就不会被序列化
        // NON_ABSENT // null的不会序列化，但如果类型是AtomicReference，依然会被序列化
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 允许字段名没有引号（可以进一步减小json体积）：
        // mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号：
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 允许出现特殊字符和转义符
        mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许C和C++样式注释：
        // mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // 序列化结果格式化，美化输出
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 枚举输出成字符串
        //WRITE_ENUMS_USING_INDEX：输出索引
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        // 空对象不要抛出异常：
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // Date、Calendar等序列化为时间格式的字符串(如果不执行以下设置，就会序列化成时间戳格式)：
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 反序列化时，遇到未知属性不要抛出异常：
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 反序列化时，遇到忽略属性不要抛出异常：
        mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        // 反序列化时，空字符串对于的实例属性为null：
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        mapper.registerModule(JavaTimeConfig.javaTimeModule());

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        mapper.registerModule(simpleModule);

        return mapper;
    }

}
