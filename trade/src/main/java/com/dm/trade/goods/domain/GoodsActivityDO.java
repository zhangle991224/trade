package com.dm.trade.goods.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 商品活动表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-01 21:57:41
 */
public class GoodsActivityDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//商品id
	private Long goodsId;
	//商品类别
	private Integer categoryId;
	//活动价格
	private Integer activityPrice;
	//秒杀数量
	private Integer activityNum;
	//商品单位 0:散装、1:整装
	private Integer unit;
	//秒杀开始时间
	private Date startTime;
	//结束时间
	private Date endTime;
	//秒杀状态，0:未开始、1:一开始、2:已结束
	private Integer status;
	//用户级别 0 不限
	private Integer userLeave;
	//活动类型 1:秒杀
	private Integer activityType;
	//创建时间
	private Date createTime;
	//
	private Date updateTime;
	//
	private String var01;
	//
	private String var02;
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
	 * 设置：商品类别
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取：商品类别
	 */
	public Integer getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置：活动价格
	 */
	public void setActivityPrice(Integer activityPrice) {
		this.activityPrice = activityPrice;
	}
	/**
	 * 获取：活动价格
	 */
	public Integer getActivityPrice() {
		return activityPrice;
	}
	/**
	 * 设置：秒杀数量
	 */
	public void setActivityNum(Integer activityNum) {
		this.activityNum = activityNum;
	}
	/**
	 * 获取：秒杀数量
	 */
	public Integer getActivityNum() {
		return activityNum;
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
	 * 设置：秒杀开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：秒杀开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：秒杀状态，0:未开始、1:一开始、2:已结束
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：秒杀状态，0:未开始、1:一开始、2:已结束
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：用户级别 0 不限
	 */
	public void setUserLeave(Integer userLeave) {
		this.userLeave = userLeave;
	}
	/**
	 * 获取：用户级别 0 不限
	 */
	public Integer getUserLeave() {
		return userLeave;
	}
	/**
	 * 设置：活动类型 1:秒杀
	 */
	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
	/**
	 * 获取：活动类型 1:秒杀
	 */
	public Integer getActivityType() {
		return activityType;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
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
}
