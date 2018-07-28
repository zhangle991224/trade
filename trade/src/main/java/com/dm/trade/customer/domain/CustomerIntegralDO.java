package com.dm.trade.customer.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户积分记录表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-03-25 11:48:05
 */
public class CustomerIntegralDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//用户id
	private Integer customerId;
	//积分数量
	private Integer integralNum;
	//订单id
	private Integer orderId;
	//消费金额
	private Integer money;

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
	 * 设置：用户id
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	/**
	 * 设置：积分数量
	 */
	public void setIntegralNum(Integer integralNum) {
		this.integralNum = integralNum;
	}
	/**
	 * 获取：积分数量
	 */
	public Integer getIntegralNum() {
		return integralNum;
	}
	/**
	 * 设置：订单id
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单id
	 */
	public Integer getOrderId() {
		return orderId;
	}
	/**
	 * 设置：消费金额
	 */
	public void setMoney(Integer money) {
		this.money = money;
	}
	/**
	 * 获取：消费金额
	 */
	public Integer getMoney() {
		return money;
	}
}
