package com.dm.trade.common.exception;

/**
 * @author zhongchao
 * @Date 2017-11-16
 * @since v1.0.0
 */
public class ApiRequestException extends RuntimeException {

    private int code;

    public ApiRequestException(String msg) {
        super(msg);
    }

    public ApiRequestException(String msg, Throwable e) {
        super(msg, e);
    }

    public ApiRequestException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public ApiRequestException(String msg, int code, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
