package com.jyx.core.utils;

import com.jyx.core.exception.CheckedException;
import com.jyx.core.exception.ServiceException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName: Assert
 * @Description: 参数校验断言
 * @Author: jyx
 * @Date: 2023-12-20 09:43
 * @Version: 1.0
 **/
public class Assert {

    public static void checkNull(Object obj, String message) {
        if (Objects.isNull(obj)) {
            throw new CheckedException(message);
        }
    }

    public static void checkEmpty(String str, String message) {
        if (Objects.isNull(str) || str.isEmpty()) {
            throw new CheckedException(message);
        }
    }

    public static void checkEmpty(Collection collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new CheckedException(message);
        }
    }

    public static void checkEmpty(Map map, String message) {
        if (MapUtils.isEmpty(map)) {
            throw new CheckedException(message);
        }
    }

    public static void checkFalse(boolean condition, String message) {
        if (!condition) {
            throw new CheckedException(message);
        }
    }

    public static void isFalse(boolean condition, String message) {
        if (!condition) {
            throw new ServiceException(message);
        }
    }

    /**
     * 数值为负数时抛出异常
     * @param number    数值
     * @param message   异常说明
     */
    public static void isMinus(Object number, String message) {
        if (isMinusNumber(number)) {
            throw new ServiceException(message);
        }
    }

    private static boolean isMinusNumber(Object value) {
        if (value instanceof Byte) {
            return (Byte) value < 0;
        } else if (value instanceof Short) {
            return (Short) value < 0;
        } else if (value instanceof Integer) {
            return (Integer) value < 0;
        } else if (value instanceof Long) {
            return (Long) value < 0;
        } else if (value instanceof Float) {
            return (Float) value < 0;
        } else if (value instanceof Double) {
            return (Double) value < 0;
        } else {
            throw new IllegalArgumentException("Unsupported number type");
        }
    }

}
