package com.dm.trade.goods.domain;

import com.dm.trade.common.utils.excel.poi.annotations.PoiEntity;
import com.dm.trade.common.utils.excel.poi.annotations.PoiFieldIndex;

import java.io.Serializable;
import java.util.Date;


/**
 * 商品类别表
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-01 21:57:40
 */
@PoiEntity(isColumnEntity = false, isRowEntity = true)
public class GoodsDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //商品id
    private Long id;
    //商品名称
    @PoiFieldIndex(index = 1)
    private String name;
    //商品类别id
    private Integer categoryId;
    //整装售价 所有金额以分为单位
    @PoiFieldIndex(index = 4)
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
    @PoiFieldIndex(index = 2)
    private String barcode;
    //规格
    @PoiFieldIndex(index = 3)
    private String specifications;
    //商品状态  0:停用 1:启用
    private Integer status = 1;
    //标签
    private String label;
    //备注
    @PoiFieldIndex(index = 5)
    private String remark;
    //修改日期
    private Date createTime;
    //
    private Date updateTime;
    // 定金金额
    private Integer var01;
    //厂家名称
    @PoiFieldIndex(index = 0)
    private String var02;
    // 单位
    @PoiFieldIndex(index = 2)
    private String var03;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 设置：商品id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：商品id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：商品名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：商品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：商品类别id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取：商品类别id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置：整装售价 所有金额以分为单位
     */
    public void setContainerPrice(Integer containerPrice) {
        this.containerPrice = containerPrice;
    }

    /**
     * 获取：整装售价 所有金额以分为单位
     */
    public Integer getContainerPrice() {
        return containerPrice;
    }

    /**
     * 设置：显示图片
     */
    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    /**
     * 获取：显示图片
     */
    public String getGoodsImg() {
        return goodsImg;
    }

    /**
     * 设置：散装价格
     */
    public void setBulkPrice(Integer bulkPrice) {
        this.bulkPrice = bulkPrice;
    }

    /**
     * 获取：散装价格
     */
    public Integer getBulkPrice() {
        return bulkPrice;
    }

    /**
     * 设置：散装起批数量
     */
    public void setBulkNum(Integer bulkNum) {
        this.bulkNum = bulkNum;
    }

    /**
     * 获取：散装起批数量
     */
    public Integer getBulkNum() {
        return bulkNum;
    }

    /**
     * 设置：是否允许退货 1:允许、0:否
     */
    public void setIsReturn(Integer isReturn) {
        this.isReturn = isReturn;
    }

    /**
     * 获取：是否允许退货 1:允许、0:否
     */
    public Integer getIsReturn() {
        return isReturn;
    }

    /**
     * 设置：条码
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 获取：条码
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * 设置：规格
     */
    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    /**
     * 获取：规格
     */
    public String getSpecifications() {
        return specifications;
    }

    /**
     * 设置：商品状态  0:停用 1:启用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：商品状态  0:停用 1:启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：标签
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 获取：标签
     */
    public String getLabel() {
        return label;
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
     * 设置：修改日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：修改日期
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
     * 设置：
     */
    public void setVar02(String var02) {
        this.var02 = var02;
    }

    /**
     * 获取：
     */
    public String getVar02() {
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "GoodsDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", containerPrice=" + containerPrice +
                ", goodsImg='" + goodsImg + '\'' +
                ", bulkPrice=" + bulkPrice +
                ", bulkNum=" + bulkNum +
                ", isReturn=" + isReturn +
                ", barcode='" + barcode + '\'' +
                ", specifications='" + specifications + '\'' +
                ", status=" + status +
                ", label='" + label + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", var01=" + var01 +
                ", var02='" + var02 + '\'' +
                ", var03='" + var03 + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
