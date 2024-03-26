package com.jyx.core.config;

import com.jyx.core.enums.DateFormat;
import com.jyx.core.utils.StringUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @ClassName: ModelMapperConfig
 * @Description: javabean转换工具 例如：DTO to VO, BO to DTO 等等
 * 官方配置说明：<a href="http://modelmapper.org/user-manual/configuration/">...</a>
 * @Author: jyx
 * @Date: 2024-03-07 16:20
 **/
@Configuration
public class ModelMapperConfig {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormat.DEFAULT_DATE_TIME_FORMAT.value());
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DateFormat.DEFAULT_DATE_FORMAT.value());
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DateFormat.DEFAULT_TIME_FORMAT.value());

    /**
     * 配置转换器 并注册到spring
     *
     * @return
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setDeepCopyEnabled(true)  // 开启深拷贝
                .setFullTypeMatchingRequired(true)  // 完全匹配
                .setMatchingStrategy(MatchingStrategies.STRICT);    // 匹配策略使用严格模式

        // date to string
        modelMapper.addConverter(localDateToStringConverter);
        modelMapper.addConverter(localDateTimeToStringConverter);
        modelMapper.addConverter(localTimeToStringConverter);
        // string to date
        modelMapper.addConverter(stringToLocalDateTimeConverter);
        modelMapper.addConverter(stringToLocalDateConverter);
        modelMapper.addConverter(stringToLocalTimeConverter);
        return modelMapper;
    }

    private final Converter<LocalDate, String> localDateToStringConverter = new AbstractConverter<>() {
        @Override
        protected String convert(final LocalDate date) {
            return Objects.isNull(date) ? null : date.format(dateFormatter);
        }
    };

    private final Converter<LocalDateTime, String> localDateTimeToStringConverter = new AbstractConverter<>() {
        @Override
        protected String convert(final LocalDateTime date) {
            return Objects.isNull(date) ? null : date.format(dateTimeFormatter);
        }
    };

    private final Converter<LocalTime, String> localTimeToStringConverter = new AbstractConverter<>() {
        @Override
        protected String convert(final LocalTime date) {
            return Objects.isNull(date) ? null : date.format(timeFormatter);
        }
    };

    private final Converter<String, LocalDateTime> stringToLocalDateTimeConverter = new AbstractConverter<>() {
        @Override
        protected LocalDateTime convert(final String dateTime) {
            return StringUtils.isEmpty(dateTime)? null :
                    LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DateFormat.DEFAULT_DATE_TIME_FORMAT.value()));
        }
    };

    private final Converter<String, LocalDate> stringToLocalDateConverter = new AbstractConverter<>() {
        @Override
        protected LocalDate convert(final String date) {
            return StringUtils.isEmpty(date)? null : LocalDate.parse(date);
        }
    };

    private final Converter<String, LocalTime> stringToLocalTimeConverter = new AbstractConverter<>() {
        @Override
        protected LocalTime convert(final String time) {
            return StringUtils.isEmpty(time)? null : LocalTime.parse(time);
        }
    };

}
