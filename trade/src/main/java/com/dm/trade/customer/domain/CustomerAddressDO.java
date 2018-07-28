package com.dm.trade.customer.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户地址表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-03-25 11:48:05
 */
public class CustomerAddressDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//
	private Long customerId;
	//省
	private Integer provinceId;
	//市
	private Integer cityId;
	//区
	private Integer areaId;
	//街道
	private String address;
	//联系人
	private String linkMan;
	//联系电话
	private String linkPhone;
	//
	private Date createTime;
	//
	private Date updateTime;

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

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
	 * 设置：省
	 */
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	/**
	 * 获取：省
	 */
	public Integer getProvinceId() {
		return provinceId;
	}
	/**
	 * 设置：市
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	/**
	 * 获取：市
	 */
	public Integer getCityId() {
		return cityId;
	}
	/**
	 * 设置：区
	 */
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	/**
	 * 获取：区
	 */
	public Integer getAreaId() {
		return areaId;
	}
	/**
	 * 设置：街道
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：街道
	 */
	public String getAddress() {
		return address;
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
}
