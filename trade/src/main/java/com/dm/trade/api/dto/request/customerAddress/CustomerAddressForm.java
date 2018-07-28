package com.dm.trade.api.dto.request.customerAddress;

import javax.validation.constraints.NotNull;

/**
 * @author zhangle
 * @title CustomerAddressForm.java
 * @Date 2018-05-04
 * @since v1.0.0
 */
public class CustomerAddressForm {

    private Long id;
//    @NotNull(message = "省不能为空")
    private Integer provinceId;

//    @NotNull(message = "市不能为空")
    private Integer cityId;

//    @NotNull(message = "区不能为空")
    private Integer areaId;

    @NotNull(message = "详细地址不能为空")
    private String address;
    //联系人
    @NotNull(message = "收货联系人不能为空")
    private String linkMan;
    //联系电话
    @NotNull(message = "收货联系电话不能为空")
    private String linkPhone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerAddressForm{" +
                "provinceId=" + provinceId +
                ", cityId=" + cityId +
                ", areaId=" + areaId +
                ", address=" + address +
                ", linkMan=" + linkMan +
                ", linkPhone=" + linkPhone +
                '}';
    }
}
