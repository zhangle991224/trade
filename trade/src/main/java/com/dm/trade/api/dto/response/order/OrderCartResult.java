package com.dm.trade.api.dto.response.order;

import java.util.Date;

/**
 * 购物车查询返回实体
 *
 * @author zhongchao
 * @title OrderCartResult.java
 * @Date 2018-04-15
 * @since v1.0.0
 */
public class OrderCartResult {
    /**
     * 主键
     */
    private Integer id;
    // 用户id
    private Long customerId;
    // 商品id
    private Long goodsId;
    //加入购物车时的单价
    private Integer unitPrice;
    //商品单位 0:散装、1:整装
    private Integer unit;
    //商品数量
    private Integer num;
    // 创建时间
    private Date updateTime;
    //商品名称
    private String name;
    //商品类别id
    private Integer categoryId;
    //显示图片
    private String goodsImg;

    // 是否失效 1. 失效
    private Integer status = 0;
    //规格
    private String specifications;

    // 定金金额
    private Integer var01;

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVar01() {
        return var01;
    }

    public void setVar01(Integer var01) {
        this.var01 = var01;
    }

    @Override
    public String toString() {
        return "OrderCartResult{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", goodsId=" + goodsId +
                ", unitPrice=" + unitPrice +
                ", unit=" + unit +
                ", num=" + num +
                ", updateTime=" + updateTime +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", goodsImg='" + goodsImg + '\'' +
                ", status=" + status +
                ", specifications='" + specifications + '\'' +
                ", var01=" + var01 +
                '}';
    }
}
