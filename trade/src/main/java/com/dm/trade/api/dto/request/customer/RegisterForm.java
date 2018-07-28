package com.dm.trade.api.dto.request.customer;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 注册表单
 *
 * @author zhongchao
 * @Date 2017-11-23
 * @since v1.0.0
 */
public class RegisterForm {

    @NotNull(message = "手机号不能为空")
    private String phone;

//    @NotNull(message = "验证码不能为空")
    private String code;

//    @Length(min = 6, message = "密码至少6位")
    private String password;

    private String openId;

    /**
     * 联系人名称
     */
    @NotNull(message = "联系人名称")
    private String linkName;

    /**
     * 主体名称
     */
    @NotNull(message = "店铺名称不能为空")
    private String comName;

    /**
     * 申请省id
     */
//    @NotNull(message = "申请省ID 不能为空")
    private Integer applyProvince;

    /**
     * 申请城市id
     */
//    @NotNull(message = "申请城市id不能为空")
    private Integer applyCity;

    /**
     * 申请区id
     */
//    @NotNull(message = "申请地区id不能为空")
    private Integer applyArea;

    /**
     * 详细地址
     */
    @NotNull(message = "地址不能为空")
    private String applyAddress;

    /**
     * 上级推广员
     */
    private String fromCustomer;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public Integer getApplyProvince() {
        return applyProvince;
    }

    public void setApplyProvince(Integer applyProvince) {
        this.applyProvince = applyProvince;
    }

    public Integer getApplyCity() {
        return applyCity;
    }

    public void setApplyCity(Integer applyCity) {
        this.applyCity = applyCity;
    }

    public Integer getApplyArea() {
        return applyArea;
    }

    public void setApplyArea(Integer applyArea) {
        this.applyArea = applyArea;
    }

    public String getApplyAddress() {
        return applyAddress;
    }

    public void setApplyAddress(String applyAddress) {
        this.applyAddress = applyAddress;
    }

    public String getFromCustomer() {
        return fromCustomer;
    }

    public void setFromCustomer(String fromCustomer) {
        this.fromCustomer = fromCustomer;
    }

    @Override
    public String toString() {
        return "RegisterForm{" +
                "phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                ", password='" + password + '\'' +
                ", linkName='" + linkName + '\'' +
                ", comName='" + comName + '\'' +
                ", applyProvince=" + applyProvince +
                ", applyCity=" + applyCity +
                ", applyArea=" + applyArea +
                ", applyAddress='" + applyAddress + '\'' +
                ", fromCustomer='" + fromCustomer + '\'' +
                '}';
    }
}
