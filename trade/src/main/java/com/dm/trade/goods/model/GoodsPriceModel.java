package com.dm.trade.goods.model;

import com.dm.trade.goods.domain.GoodsDO;

/**
 * 商品价格model
 *
 * @author zhongchao
 * @title GoodsPriceModel.java
 * @Date 2018-04-22
 * @since v1.0.0
 */
public class GoodsPriceModel {

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 加入购物车时的单价(散装价格)
     */
    private Integer bulkPrice;

    /**
     * 加入购物车时的单价(整装价格)
     */
    private Integer containerPrice;

    /**
     * 定金金额ø
     */
    private Integer var01;

    /**
     * 是否为活动
     */
    private boolean isActivity = false;

    /**
     * 类目id
     */
    private Integer categoryId;

    public GoodsPriceModel() {
    }

    public GoodsPriceModel(Long goodsId) {
        this.goodsId = goodsId;
    }

    public GoodsPriceModel(Long goodsId, Integer bulkPrice, Integer containerPrice) {
        this.goodsId = goodsId;
        this.bulkPrice = bulkPrice;
        this.containerPrice = containerPrice;
    }

    /**
     * 转换
     *
     * @param goodsDO
     * @return
     */
    public static GoodsPriceModel goodsPriceModel(GoodsDO goodsDO) {
        GoodsPriceModel goodsPriceModel = new GoodsPriceModel(goodsDO.getId(), goodsDO.getBulkPrice(), goodsDO.getContainerPrice());
        goodsPriceModel.setVar01(goodsDO.getVar01());
        goodsPriceModel.setCategoryId(goodsDO.getCategoryId());
        return goodsPriceModel;
    }

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

    public Integer getContainerPrice() {
        return containerPrice;
    }

    public void setContainerPrice(Integer containerPrice) {
        this.containerPrice = containerPrice;
    }

    public boolean isActivity() {
        return isActivity;
    }

    public void setActivity(boolean activity) {
        isActivity = activity;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getVar01() {
        return var01;
    }

    public void setVar01(Integer var01) {
        this.var01 = var01;
    }

    @Override
    public String toString() {
        return "GoodsPriceModel{" +
                "goodsId=" + goodsId +
                ", bulkPrice=" + bulkPrice +
                ", containerPrice=" + containerPrice +
                ", var01=" + var01 +
                ", isActivity=" + isActivity +
                ", categoryId=" + categoryId +
                '}';
    }
}
