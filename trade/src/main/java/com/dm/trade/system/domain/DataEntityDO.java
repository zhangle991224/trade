package com.dm.trade.system.domain;

import java.util.Date;

/**
 * Created by Intellij idea
 * User: liu.y
 * Date: 2017/11/19
 * Description:
 * To change this template use File | Setting | File and Code Templates
 */
public class DataEntityDO {
    private String remark;
    private Date createdate;
    private Date updatedate;
    private String var01;
    private String var02;
    private String var03;
    private String var04;
    private String var05;

    @Override
    public String toString() {
        return "DataEntityDO{" +
                "remark='" + remark + '\'' +
                ", createdate=" + createdate +
                ", updatedate=" + updatedate +
                ", var01='" + var01 + '\'' +
                ", var02='" + var02 + '\'' +
                ", var03='" + var03 + '\'' +
                ", var04='" + var04 + '\'' +
                ", var05='" + var05 + '\'' +
                '}';
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getVar01() {
        return var01;
    }

    public void setVar01(String var01) {
        this.var01 = var01;
    }

    public String getVar02() {
        return var02;
    }

    public void setVar02(String var02) {
        this.var02 = var02;
    }

    public String getVar03() {
        return var03;
    }

    public void setVar03(String var03) {
        this.var03 = var03;
    }

    public String getVar04() {
        return var04;
    }

    public void setVar04(String var04) {
        this.var04 = var04;
    }

    public String getVar05() {
        return var05;
    }

    public void setVar05(String var05) {
        this.var05 = var05;
    }
}
