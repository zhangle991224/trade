package com.dm.trade.api.dto.request.order;

import javax.validation.constraints.NotNull;

/**
 * @author zhongchao
 * @title OrderCartForm.java
 * @Date 2018-04-15
 * @since v1.0.0
 */
public class OrderCartForm {

    // 商品id
    @NotNull(message = "商品id不能为空")
    private Long goodsId;

    //加入购物车时的单价(散装价格)
    private Integer bulkPrice;

    // 散装数量
    private Integer bulkNum;

    //加入购物车时的单价(整装价格)
    private Integer containerPrice;

    //整件数量
    private Integer containerNum;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getBulkPrice() {
        return bulkPrice;
    }

    public void setBulkPrice(Integer bulkPrice) {
        this.bulkPrice = bulkPrice;
    }

    public Integer getBulkNum() {
        return bulkNum;
    }

    public void setBulkNum(Integer bulkNum) {
        this.bulkNum = bulkNum;
    }

    public Integer getContainerPrice() {
        return containerPrice;
    }

    public void setContainerPrice(Integer containerPrice) {
        this.containerPrice = containerPrice;
    }

    public Integer getContainerNum() {
        return containerNum;
    }

    public void setContainerNum(Integer containerNum) {
        this.containerNum = containerNum;
    }

    @Override
    public String toString() {
        return "OrderCartForm{" +
                "goodsId=" + goodsId +
                ", bulkPrice=" + bulkPrice +
                ", bulkNum=" + bulkNum +
                ", containerPrice=" + containerPrice +
                ", containerNum=" + containerNum +
                '}';
    }
}
