package com.dm.trade.order.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 订单详情表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-15 15:53:41
 */
public class OrderDetailDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//订单id
	private Long orderId;
	//用户id
	private Long customerId;
	//商品id
	private Long goodsId;
	//商品单位 0:散装、1:整装
	private Integer unit;
	//
	private Integer num;
	//单价（购买单位的单价）
	private Integer unitPrice;
	//总价
	private Integer totalPrice;
	//活动单价
	private Integer activityPrice;
	//
	private Date createTime;
	//
	private Date updateTime;
	//
	private String var01;
	//
	private String var02;

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
	 * 设置：订单id
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单id
	 */
	public Long getOrderId() {
		return orderId;
	}
	/**
	 * 设置：用户id
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getCustomerId() {
		return customerId;
	}
	/**
	 * 设置：商品id
	 */
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	/**
	 * 获取：商品id
	 */
	public Long getGoodsId() {
		return goodsId;
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
	 * 设置：
	 */
	public void setNum(Integer num) {
		this.num = num;
	}
	/**
	 * 获取：
	 */
	public Integer getNum() {
		return num;
	}
	/**
	 * 设置：单价（购买单位的单价）
	 */
	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}
	/**
	 * 获取：单价（购买单位的单价）
	 */
	public Integer getUnitPrice() {
		return unitPrice;
	}
	/**
	 * 设置：总价
	 */
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * 获取：总价
	 */
	public Integer getTotalPrice() {
		return totalPrice;
	}
	/**
	 * 设置：活动单价
	 */
	public void setActivityPrice(Integer activityPrice) {
		this.activityPrice = activityPrice;
	}
	/**
	 * 获取：活动单价
	 */
	public Integer getActivityPrice() {
		return activityPrice;
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
	 * 设置：
	 */
	public void setVar01(String var01) {
		this.var01 = var01;
	}
	/**
	 * 获取：
	 */
	public String getVar01() {
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
}
