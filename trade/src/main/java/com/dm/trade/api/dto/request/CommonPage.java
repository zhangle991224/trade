package com.dm.trade.api.dto.request;

/**
 * @author zhongchao
 * @Date 2017-11-19
 * @since v1.0.0
 */
public class CommonPage {
    private Integer offset = 1;

    private Integer limit = 10;

    public Integer getOffset() {
        return (offset - 1) * limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
