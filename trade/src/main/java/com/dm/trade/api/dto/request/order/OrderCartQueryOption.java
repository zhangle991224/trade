package com.dm.trade.api.dto.request.order;

import com.dm.trade.api.dto.request.CommonPage;

/**
 * @author zhongchao
 * @title OrderCartQueryOption.java
 * @Date 2018-04-15
 * @since v1.0.0
 */
public class OrderCartQueryOption extends CommonPage {

    /**
     * 商品id
     */
    private Long goodsId;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "OrderCartQueryOption{" +
                "goodsId=" + goodsId +
                '}';
    }
}
