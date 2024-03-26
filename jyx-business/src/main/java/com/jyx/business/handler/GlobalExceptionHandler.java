package com.jyx.business.handler;

import com.jyx.core.base.domain.R;
import com.jyx.core.enums.ResponseStatus;
import com.jyx.core.exception.ServiceException;
import com.jyx.core.exception.user.BlackListException;
import com.jyx.core.exception.user.CaptchaException;
import com.jyx.core.exception.user.CaptchaExpireException;
import com.jyx.core.exception.user.UserPasswordNotMatchException;
import com.jyx.core.utils.StringUtils;
import com.jyx.system.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @ClassName: GlobalExceptionHandler
 * @Description: 全局异常处理
 * @Author: jyx
 * @Date: 2024-03-07 15:06
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     *
     * @param e
     * @param request
     * @return error info
     */
    @ExceptionHandler(ServiceException.class)
    public R handleServiceException(ServiceException e, HttpServletRequest request) {
        Integer code = e.getCode();
        log.error("business exception, url:{}, error msg:{}", request.getRequestURI(), e.getMessage());
        return StringUtils.isNotNull(code) ? R.fail(code, e.getMessage())
                : R.fail(ResponseStatus.BUSINESS_EXCEPTION.value(), e.getMessage());
    }

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public R handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return R.fail(HttpStatus.FORBIDDEN.value(), "没有权限，请联系管理员授权");
    }

    /**
     * 请求路径中缺少必需的路径变量
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public R handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求路径中缺少必需的路径变量'{}',发生系统异常.", requestURI, e);
        return R.fail(ResponseStatus.MISSING_PATH_VARIABLE.value(),
                String.format("请求路径中缺少必需的路径变量[%s]", e.getVariableName()));
    }

    @ExceptionHandler(UserPasswordNotMatchException.class)
    public R handleUserPasswordNotMatchException(UserPasswordNotMatchException e, HttpServletRequest request) {
        log.warn("用户密码不匹配");
        return R.fail(ResponseStatus.USER_PASSWORD_NOT_MATCH.value(), "用户名密码不匹配");
    }

    @ExceptionHandler(CaptchaExpireException.class)
    public R handleCaptchaExpireException(CaptchaExpireException e, HttpServletRequest request) {
        log.warn("验证码失效");
        return R.fail(ResponseStatus.CAPTCHA_EXPIRE.value(), "验证码失效");
    }

    @ExceptionHandler(CaptchaException.class)
    public R handleCaptchaException(CaptchaException e, HttpServletRequest request) {
        log.warn("验证码不匹配");
        return R.fail(ResponseStatus.CAPTCHA_NOT_MATCH.value(), "验证码不匹配");
    }

    /**
     * 请求参数类型不匹配
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求参数类型不匹配'{}'", requestURI, e);
        return R.fail(
                ResponseStatus.PARAM_TYPE_NOT_MATCH.value(),
                String.format(
                        "请求参数类型不匹配，参数[%s]要求类型为：'%s'，但输入值为：'%s'",
                        e.getName(),
                        Objects.requireNonNull(e.getRequiredType()).getName(),
                        e.getValue()));
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return R.fail(ResponseStatus.RUNTIME_EXCEPTION.value(), e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return R.fail(ResponseStatus.UNKNOWN_EXCEPTION.value(), e.getMessage());
    }

    /**
     * http接口参数@Valid异常
     *
     * @param e
     * @param request
     * @return error info
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String message;
        try {
            message = e.getMessage().substring(e.getMessage().lastIndexOf("[") + 1, e.getMessage().lastIndexOf("]") - 1);
        } catch (Exception ex) {
            log.error("@Validated截取异常信息失效,已重置,所有异常信息为:{}", e.getMessage());
            message = "请求参数错误";
        }
        log.warn("request param is error, url:{}, error msg:{}", request.getRequestURI(), message);
        return R.fail(HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 请求方法不支持
     *
     * @param e
     * @param request
     * @return error info
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.warn("method not support, url:{}, error msg:{}", request.getRequestURI(), e.getMessage());
        return R.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }

    /**
     * 参数绑定异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public R handleBindException(BindException e) {
        log.warn("param bind exception, error msg:{}", e.getMessage());
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return R.fail(HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 黑名单处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BlackListException.class)
    public R handleBlackListException(BlackListException e) {
        log.warn("param bind exception, error msg:{}", e.getMessage());
        return R.fail(MessageUtils.message("login.blocked"));
    }

}
