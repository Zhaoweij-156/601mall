package com.mall.exception;

import com.mall.dto.Result;
import com.mall.dto.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理被拦截的异常，未登录请求需要登录才能访问的接口抛出
     *
     * @param e NoPermissionException
     * @return Result
     */
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    @ExceptionHandler(value = NoLoginException.class)
    public Result exceptionHandler(NoLoginException e) {
        log.error("权限不足，无法请求：-----------{}", e.getMessage());
        e.printStackTrace();
        return Result.genResult(ResultEnum.NO_LOGIN, null);
    }

    /**
     * token验证出现异常，一般是未登录或者登录过期导致
     *
     * @param e TokenVerifyException
     * @return Result
     */
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    @ExceptionHandler(value = TokenVerifyException.class)
    public Result exceptionHandler(TokenVerifyException e) {
        log.error("token验证异常，无法请求：-----------{}", e.getMessage());
        e.printStackTrace();
        return Result.genResult(ResultEnum.TOKEN_VERIFY_ERROR, null);
    }

    /**
     * 处理不合法参数异常
     *
     * @param e IllegalArgumentException
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result exceptionHandler(IllegalArgumentException e) {
        log.error("Assert异常：-----------{}", e.getMessage());
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }

    /**
     * 处理运行时异常
     *
     * @param e RuntimeException
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result exceptionHandler(RuntimeException e) {
        log.error("运行时异常：-----------{}", e.getMessage());
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }

    /**
     * 兜底的Exception处理，处理未知异常
     *
     * @param e Exception
     * @return Result
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error("发生未知异常: -----------{}", e.getMessage());
        e.printStackTrace();
        return Result.genResult(ResultEnum.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
