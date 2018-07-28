package com.dm.trade.business.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 商家信息表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-06-08 21:41:41
 */
public class BusinessDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//联系人姓名
	private String contactPeopleName;
	//联系人电话
	private String contactPeoplePhone;
	//商家名称
	private String businessName;
	//商家电话
	private String businessPhone;
	//商家地址
	private String businessAddress;
	//备注
	private String businessMemo;
	//
	private Date createTime;
	//
	private Date updateTime;

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
	 * 设置：联系人姓名
	 */
	public void setContactPeopleName(String contactPeopleName) {
		this.contactPeopleName = contactPeopleName;
	}
	/**
	 * 获取：联系人姓名
	 */
	public String getContactPeopleName() {
		return contactPeopleName;
	}
	/**
	 * 设置：联系人电话
	 */
	public void setContactPeoplePhone(String contactPeoplePhone) {
		this.contactPeoplePhone = contactPeoplePhone;
	}
	/**
	 * 获取：联系人电话
	 */
	public String getContactPeoplePhone() {
		return contactPeoplePhone;
	}
	/**
	 * 设置：商家名称
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	/**
	 * 获取：商家名称
	 */
	public String getBusinessName() {
		return businessName;
	}
	/**
	 * 设置：商家电话
	 */
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	/**
	 * 获取：商家电话
	 */
	public String getBusinessPhone() {
		return businessPhone;
	}
	/**
	 * 设置：商家地址
	 */
	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}
	/**
	 * 获取：商家地址
	 */
	public String getBusinessAddress() {
		return businessAddress;
	}
	/**
	 * 设置：备注
	 */
	public void setBusinessMemo(String businessMemo) {
		this.businessMemo = businessMemo;
	}
	/**
	 * 获取：备注
	 */
	public String getBusinessMemo() {
		return businessMemo;
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
