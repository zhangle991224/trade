package com.dm.trade.api.dto.response.order;

/**
 * @author zhongchao
 * @title OrderDetailResult.java
 * @Date 2018-05-06
 * @since v1.0.0
 */
public class OrderDetailResult {

    //商品id
    private Long id;
    //商品名称
    private String name;
    //商品类别id
    private Integer categoryId;
    //显示图片
    private String goodsImg;

    /**
     * 单位
     */
    private Integer unit;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 单价
     */
    private Integer unitPrice;

    /**
     * 总价
     */
    private Integer totalPrice;

    /**
     * 活动价
     */
    private Integer activityPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(Integer activityPrice) {
        this.activityPrice = activityPrice;
    }

    @Override
    public String toString() {
        return "OrderDetailResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", goodsImg='" + goodsImg + '\'' +
                ", unit=" + unit +
                ", num=" + num +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                ", activityPrice=" + activityPrice +
                '}';
    }
}
