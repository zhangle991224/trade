package com.dm.trade.api.dto.request.h5;

import java.io.Serializable;

/**
 * @author zhangle
 * @title GoodsListQueryOptionH5.java
 * @Date 2018-07-21
 * @since v1.0.0
 */
public class GoodsListQueryOptionH5 implements Serializable {
    /**
     * 类目id
     */
    private Long categoryId;

    /**
     * 商品名称
     */
    private String name;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GoodsListQueryOptionH5{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}
