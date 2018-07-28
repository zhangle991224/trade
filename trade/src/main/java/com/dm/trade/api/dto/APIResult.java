package com.dm.trade.api.dto;


import com.dm.trade.common.utils.R;

/**
 * @author zhongchao
 * @Date 2017-11-16
 * @since v1.0.0
 */
public class APIResult extends R {

    private static final long serialVersionUID = 7875192698489046656L;

    private static final String DATA = "data";

    public APIResult(Object data) {
        super();
        put(DATA, data);
    }

    public APIResult() {
    }

    public static APIResult isOk(Object data) {
        return new APIResult(data);
    }

    public static APIResult error(int code, String msg) {
        APIResult r = new APIResult();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

}
