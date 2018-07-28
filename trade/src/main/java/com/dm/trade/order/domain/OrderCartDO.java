package com.dm.trade.order.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 购物车
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-15 15:53:41
 */
public class OrderCartDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //
    private Long customerId;
    //
    private Long goodsId;
    //加入购物车时的单价
    private Integer unitPrice;
    //商品单位 0:散装、1:整装
    private Integer unit;
    //商品数量
    private Integer num;
    //
    private Date createTime;
    //
    private Date updateTime;
    // 类别
    private Integer var01;
    // 供应商id
    private Integer var02;
    //
    private String var03;

    /**
     * 设置：
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置：
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取：
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 设置：
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取：
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * 设置：加入购物车时的单价
     */
    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 获取：加入购物车时的单价
     */
    public Integer getUnitPrice() {
        return unitPrice;
    }

    /**
     * 设置：商品单位 0:散装、1:整装
     */
    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    /**
     * 获取：商品单位 0:散装、1:整装
     */
    public Integer getUnit() {
        return unit;
    }

    /**
     * 设置：商品数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取：商品数量
     */
    public Integer getNum() {
        return num;
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
     * 设置：类别
     */
    public void setVar01(Integer var01) {
        this.var01 = var01;
    }

    /**
     * 获取：类别
     */
    public Integer getVar01() {
        return var01;
    }

    /**
     * 设置：供应商id
     */
    public void setVar02(Integer var02) {
        this.var02 = var02;
    }

    /**
     * 获取：供应商id
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
        return "OrderCartDO{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", goodsId=" + goodsId +
                ", unitPrice=" + unitPrice +
                ", unit=" + unit +
                ", num=" + num +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", var01=" + var01 +
                ", var02=" + var02 +
                ", var03='" + var03 + '\'' +
                '}';
    }
}
