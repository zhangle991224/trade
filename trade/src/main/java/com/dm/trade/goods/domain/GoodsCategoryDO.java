package com.dm.trade.goods.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 商品类别表
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-01 21:57:41
 */
public class GoodsCategoryDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //类别名称
    private String name;
    //父类别id，顶级类别为0
    private Integer pid;
    // 商户id
    private Integer businessId;
    //创建时间
    private Date createTimne;
    //修改日期
    private Date updateTime;

    /**
     * 设置：主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置：类别名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：类别名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：父类别id，顶级类别为0
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取：父类别id，顶级类别为0
     */
    public Integer getPid() {
        return pid;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTimne(Date createTimne) {
        this.createTimne = createTimne;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTimne() {
        return createTimne;
    }

    /**
     * 设置：修改日期
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：修改日期
     */
    public Date getUpdateTime() {
        return updateTime;
    }
}
