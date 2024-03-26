package com.jyx.core.exception;

/**
 * @ClassName: UtilException
 * @Description: 工具类异常
 * @Author: tgb
 * @Date: 2024-03-04 11:44
 * @Version: 1.0
 **/
public class UtilException extends RuntimeException {
    public UtilException(Throwable e) {
        super(e.getMessage(), e);
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

