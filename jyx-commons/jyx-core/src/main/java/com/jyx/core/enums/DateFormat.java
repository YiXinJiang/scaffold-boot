package com.jyx.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: DataFormat
 * @Description: 时间格式
 * @Author: jyx
 * @Date: 2024-03-13 17:11
 **/
@AllArgsConstructor
public enum DateFormat {

    DEFAULT_DATE_TIME_FORMAT("yyyy-MM-dd HH:mm:ss", TimeZone.GREENWICH_MEAN_TIME),
    DEFAULT_DATE_FORMAT("yyyy-MM-dd", TimeZone.GREENWICH_MEAN_TIME),
    DEFAULT_TIME_FORMAT("HH:mm:ss", TimeZone.GREENWICH_MEAN_TIME),

    ;

    private final String value;

    @Getter
    private final DateFormat.TimeZone timeZone;

    public String value() {
        return this.value;
    }

    @AllArgsConstructor
    public enum TimeZone {
        /**
         * 格林威治
         */
        GREENWICH_MEAN_TIME("GMT+8"),
        ;

        private final String value;

        public String value() {
            return this.value;
        }
    }

}
