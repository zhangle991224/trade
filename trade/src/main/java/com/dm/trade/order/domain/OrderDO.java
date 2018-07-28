package com.dm.trade.order.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 订单表
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-15 15:53:40
 */
public class OrderDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //总金额
    private Integer totalmoney;
    //
    private Long customerId;
    //是否支付 0:未支付、1:已支付、2:已过期
    private Integer isPaid;
    //支付类型 1:微信、2:支付宝、3:线下
    private Integer payType;
    //支付时间
    private Date payTime;
    //地址id
    private Long addressId;
    //订单状态 0: 下单未支付,1:等待派送,2:派送中,3:已收货
    private Integer status;
    //
    private Date createTime;
    //
    private Date updateTime;
    //备注
    private String remark;
    // 定金金额
    private Integer var01;
    // 供应商id
    private Integer var02;
    //
    private String var03;

    /**
     * 设置：
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：总金额
     */
    public void setTotalmoney(Integer totalmoney) {
        this.totalmoney = totalmoney;
    }

    /**
     * 获取：总金额
     */
    public Integer getTotalmoney() {
        return totalmoney;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 设置：是否支付 0:未支付、1:已支付、2:已过期
     */
    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }

    /**
     * 获取：是否支付 0:未支付、1:已支付、2:已过期
     */
    public Integer getIsPaid() {
        return isPaid;
    }

    /**
     * 设置：支付类型 1:微信、2:支付宝、3:线下
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * 获取：支付类型 1:微信、2:支付宝、3:线下
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * 设置：支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取：支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置：地址id
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    /**
     * 获取：地址id
     */
    public Long getAddressId() {
        return addressId;
    }

    /**
     * 设置：订单状态 0: 下单未支付,1:等待派送,2:派送中,3:已收货
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：订单状态 0: 下单未支付,1:等待派送,2:派送中,3:已收货
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置：备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取：备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置：
     */
    public void setVar01(Integer var01) {
        this.var01 = var01;
    }

    /**
     * 获取：
     */
    public Integer getVar01() {
        return var01;
    }

    /**
     * 设置：供应商
     */
    public void setVar02(Integer var02) {
        this.var02 = var02;
    }

    /**
     * 获取：供应商
     */
    public Integer getVar02() {
        return var02;
    }

    /**
     * 设置：
     */
    public void setVar03(String var03) {
        this.var03 = var03;
    }

    /**
     * 获取：
     */
    public String getVar03() {
        return var03;
    }

    @Override
    public String toString() {
        return "OrderDO{" +
                "id=" + id +
                ", totalmoney=" + totalmoney +
                ", customerId=" + customerId +
                ", isPaid=" + isPaid +
                ", payType=" + payType +
                ", payTime=" + payTime +
                ", addressId=" + addressId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                ", var01=" + var01 +
                ", var02='" + var02 + '\'' +
                ", var03='" + var03 + '\'' +
                '}';
    }
}
