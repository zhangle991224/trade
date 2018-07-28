package com.dm.trade.common.exception;

/**
 * @author zhongchao
 * @title PayException.java
 * @Date 2018-05-06
 * @since v1.0.0
 */
public class PayException extends RuntimeException {
    private static final long serialVersionUID = -1522316942570561024L;
    private int code;

    public PayException(String msg) {
        super(msg);
    }

    public PayException(String msg, Throwable e) {
        super(msg, e);
    }

    public PayException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public PayException(String msg, int code, Throwable e) {
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
