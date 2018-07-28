package com.dm.trade.api.dto;

import java.io.Serializable;

/**
 * @author zhongchao
 * @title Code.java
 * @Date 2017-12-12
 * @since v1.0.0
 */
public class Code implements Serializable {

    private static final long serialVersionUID = -4656202767421059928L;
    private String code;

    private long time;

    public Code(String code, long time) {
        this.code = code;
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
