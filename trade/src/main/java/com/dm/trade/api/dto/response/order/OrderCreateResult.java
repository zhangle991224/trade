package com.dm.trade.api.dto.response.order;

import java.util.List;

/**
 * @author zhongchao
 * @title OrderCreateResult.java
 * @Date 2018-05-22
 * @since v1.0.0
 */
public class OrderCreateResult {

    /**
     * 订单id
     */
    private List<Long> orderId;

    /**
     * 是否需要支付 1 是 0 否
     */
    private Integer needPay;

    public OrderCreateResult() {
    }

    public OrderCreateResult(List<Long> orderId, Integer needPay) {
        this.orderId = orderId;
        this.needPay = needPay;
    }

    public List<Long> getOrderId() {
        return orderId;
    }

    public void setOrderId(List<Long> orderId) {
        this.orderId = orderId;
    }

    public Integer getNeedPay() {
        return needPay;
    }

    public void setNeedPay(Integer needPay) {
        this.needPay = needPay;
    }

    @Override
    public String toString() {
        return "OrderCreateResult{" +
                "orderId=" + orderId +
                ", needPay=" + needPay +
                '}';
    }
}
