package com.dm.trade.api.dto.response.goods;

/**
 * 商品返回对象
 */
public class GoodsListResult {

    //商品id
    private Long id;
    //商品名称
    private String name;

    /**
     * 备注
     */
    private String remark;
    //商品类别id
    private Integer categoryId;
    //整装售价 所有金额以分为单位
    private Integer containerPrice;
    //显示图片
    private String goodsImg;
    //散装价格
    private Integer bulkPrice;
    //散装起批数量
    private Integer bulkNum;
    //是否允许退货 1:允许、0:否
    private Integer isReturn;
    //条码
    private String barcode;
    //规格
    private String specifications;
    //标签
    private String label;
    //定金金额
    private Integer var01;

    // 厂家
    private String var02;

    // 单位  件、箱
    private String var03;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getContainerPrice() {
        return containerPrice;
    }

    public void setContainerPrice(Integer containerPrice) {
        this.containerPrice = containerPrice;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
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

    public Integer getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Integer isReturn) {
        this.isReturn = isReturn;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getVar01() {
        return var01;
    }

    public void setVar01(Integer var01) {
        this.var01 = var01;
    }

    public String getVar02() {
        return var02;
    }

    public void setVar02(String var02) {
        this.var02 = var02;
    }

    public String getVar03() {
        return var03;
    }

    public void setVar03(String var03) {
        this.var03 = var03;
    }

    @Override
    public String toString() {
        return "GoodsListResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", categoryId=" + categoryId +
                ", containerPrice=" + containerPrice +
                ", goodsImg='" + goodsImg + '\'' +
                ", bulkPrice=" + bulkPrice +
                ", bulkNum=" + bulkNum +
                ", isReturn=" + isReturn +
                ", barcode='" + barcode + '\'' +
                ", specifications='" + specifications + '\'' +
                ", label='" + label + '\'' +
                ", var02=" + var02 +
                '}';
    }
}
