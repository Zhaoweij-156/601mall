package com.mall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 权限不足异常
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/25 20:09
 */
@ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
public class NoLoginException extends RuntimeException {

    public NoLoginException() {
    }

    public NoLoginException(String message) {
        super(message);
    }

    public NoLoginException(String message, Throwable cause) {
        super(message, cause);
    }

}
