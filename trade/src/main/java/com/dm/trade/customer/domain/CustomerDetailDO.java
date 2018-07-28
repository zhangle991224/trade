package com.dm.trade.customer.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户详情表
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-03-25 11:48:05
 */
public class CustomerDetailDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //
    private Long customerId;
    //申请省id
    private Integer applyProvince;
    //申请区id
    private Integer applyCity;
    //申请区id
    private Integer applyArea;
    //
    private String applyAddress;
    //
    private String name;
    //公司（门店）名称
    private String comName;
    //联系人
    private String linkman;
    //联系方式
    private String linkPhone;
    //
    private Date createTime;
    //
    private Date updateTime;
    //来源上级  customer_id
    private String fromCustomer;
    //
    private String var01;
    //
    private String var02;
    //
    private String var03;

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
     * 设置：申请省id
     */
    public void setApplyProvince(Integer applyProvince) {
        this.applyProvince = applyProvince;
    }

    /**
     * 获取：申请省id
     */
    public Integer getApplyProvince() {
        return applyProvince;
    }

    /**
     * 设置：申请区id
     */
    public void setApplyCity(Integer applyCity) {
        this.applyCity = applyCity;
    }

    /**
     * 获取：申请区id
     */
    public Integer getApplyCity() {
        return applyCity;
    }

    /**
     * 设置：申请区id
     */
    public void setApplyArea(Integer applyArea) {
        this.applyArea = applyArea;
    }

    /**
     * 获取：申请区id
     */
    public Integer getApplyArea() {
        return applyArea;
    }

    /**
     * 设置：
     */
    public void setApplyAddress(String applyAddress) {
        this.applyAddress = applyAddress;
    }

    /**
     * 获取：
     */
    public String getApplyAddress() {
        return applyAddress;
    }

    /**
     * 设置：
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：公司（门店）名称
     */
    public void setComName(String comName) {
        this.comName = comName;
    }

    /**
     * 获取：公司（门店）名称
     */
    public String getComName() {
        return comName;
    }

    /**
     * 设置：联系人
     */
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    /**
     * 获取：联系人
     */
    public String getLinkman() {
        return linkman;
    }

    /**
     * 设置：联系方式
     */
    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    /**
     * 获取：联系方式
     */
    public String getLinkPhone() {
        return linkPhone;
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
     * 设置：来源上级  customer_id
     */
    public void setFromCustomer(String fromCustomer) {
        this.fromCustomer = fromCustomer;
    }

    /**
     * 获取：来源上级  customer_id
     */
    public String getFromCustomer() {
        return fromCustomer;
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
