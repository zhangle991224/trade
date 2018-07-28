package com.dm.trade.customer.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户信息 主表
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-03-25 11:29:15
 */
public class CustomerDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //
    private String phone;
    //
    private String password;
    //用户等级
    private Integer leave;
    //真实姓名
    private String realName;
    //昵称
    private String nickName;
    //用户类型 1:个体、2:餐饮、3:酒店、4:门店
    private Integer type;
    //用户状态 0:待审核、1:正常、2:停用
    private Integer status;
    //
    private Date createTime;
    //
    private Date updateTime;
    //微信openid
    private String openId;
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
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取：
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置：
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取：
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置：用户等级
     */
    public void setLeave(Integer leave) {
        this.leave = leave;
    }

    /**
     * 获取：用户等级
     */
    public Integer getLeave() {
        return leave;
    }

    /**
     * 设置：真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取：真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置：昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取：昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置：用户类型 1:个体、2:餐饮、3:酒店、4:门店
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取：用户类型 1:个体、2:餐饮、3:酒店、4:门店
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置：用户状态 0:待审核、1:正常、2:停用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：用户状态 0:待审核、1:正常、2:停用
     */
    public Integer getStatus() {
        return status;
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
     * 设置：微信openid
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取：微信openid
     */
    public String getOpenId() {
        return openId;
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
