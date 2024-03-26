package com.jyx.core.config;

import com.jyx.core.enums.DateFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ClassName: SpringConvertConfig
 * @Description: spring入参转换器
 * @Author: jyx
 * @Date: 2024-03-13 17:10
 **/
@Configuration
public class SpringConvertConfig {

    /**
     * 入参格式化
     * -----------------------------------
     * warn: 请勿使用lambda表达式优化以下几个转换器 否则会抛出泛型未指定的错误
     * -----------------------------------
     *
     * @return
     */
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern(DateFormat.DEFAULT_DATE_FORMAT.value()));
            }
        };
    }

    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DateFormat.DEFAULT_DATE_TIME_FORMAT.value()));
            }
        };
    }

    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                return LocalTime.parse(source, DateTimeFormatter.ofPattern(DateFormat.DEFAULT_TIME_FORMAT.value()));
            }
        };
    }

    @Bean
    public Converter<String, Date> dateConverter() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat format = new SimpleDateFormat(DateFormat.DEFAULT_DATE_TIME_FORMAT.value());
                try {
                    return format.parse(source);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

}
