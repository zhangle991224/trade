package com.dm.trade.common.exception;

/**
 * @author zhongchao
 * @Date 2017-11-15
 * @since v1.0.0
 */
public class ApiRequestUnknownException extends RuntimeException {

    private static final long serialVersionUID = 3133906143173497271L;
    private int code;

    public ApiRequestUnknownException(String msg) {
        super(msg);
        this.code = 500;
    }

    public ApiRequestUnknownException(String msg, Throwable e) {
        super(msg, e);
        this.code = 500;
    }

    public int getCode() {
        return code;
    }
}
