package com.dm.trade.api.dto.request.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 通过购物车 创建订单
 *
 * @author zhongchao
 * @title OrderAddForm.java
 * @Date 2018-04-15
 * @since v1.0.0
 */
public class OrderAddForm {

    /**
     * 购物车id
     */
    @NotNull(message = "购物车id不能为空")
    private List<Long> ids;

    /**
     * 支付价格
     * 暂时不做
     */
//    @NotNull(message = "支付金额不能为空")
//    @Min(value = 1)
    private Integer payMoney;


    /**
     * 订单金额
     */
    @NotNull(message = "订单金额")
    @Min(value = 1)
    private Integer orderMoney;

    /**
     * 支付方式 默认微信支付
     */
    private Integer payType = 1;

    /**
     * 收货地址id
     */
    @NotNull(message = "收货地址不能为空")
    private Long addressId;

    /**
     * 备注
     */
    private String remark;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Integer getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Integer orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OrderAddForm{" +
                "ids=" + ids +
                ", payMoney=" + payMoney +
                ", orderMoney=" + orderMoney +
                ", payType=" + payType +
                ", addressId=" + addressId +
                ", remark='" + remark + '\'' +
                '}';
    }
}
