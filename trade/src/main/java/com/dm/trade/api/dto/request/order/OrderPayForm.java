package com.dm.trade.api.dto.request.order;

import javax.validation.constraints.NotNull;

/**
 * 订单支付参数
 *
 * @author zhongchao
 * @title OrderPayForm.java
 * @Date 2018-05-22
 * @since v1.0.0
 */
public class OrderPayForm {

    /**
     * 订单id
     */
    @NotNull
    private Long orderId;

    /**
     * 支付类型
     */
    @NotNull
    private Integer payType;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "OrderPayForm{" +
                "orderId=" + orderId +
                ", payType=" + payType +
                '}';
    }
}
