package com.mall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * token验证异常
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/25 20:21
 */
@ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
public class TokenVerifyException extends RuntimeException {

    public TokenVerifyException() {
    }

    public TokenVerifyException(String message) {
        super(message);
    }

    public TokenVerifyException(String message, Throwable cause) {
        super(message, cause);
    }

}
