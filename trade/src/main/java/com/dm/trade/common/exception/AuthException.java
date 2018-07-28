package com.dm.trade.common.exception;

/**
 * @author zhongchao
 * @title AuthException.java
 * @Date 2017-12-15
 * @since v1.0.0
 */
public class AuthException extends RuntimeException {

    private int code;

    public AuthException() {
        this.code = 301;
    }

    public AuthException(String message) {
        super(message);
        this.code = 301;
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
        this.code = 301;
    }

    public AuthException(Throwable cause) {
        super(cause);
        this.code = 301;
    }

    public AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = 301;
    }

    public int getCode() {
        return code;
    }
}
