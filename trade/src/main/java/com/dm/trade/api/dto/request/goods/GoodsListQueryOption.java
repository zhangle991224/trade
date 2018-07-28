package com.dm.trade.api.dto.request.goods;

import com.dm.trade.api.dto.request.CommonPage;

public class GoodsListQueryOption extends CommonPage {

    private long id;

    /**
     * 类目id
     */
    private Long categoryId;

    /**
     * 商品名称 可模糊查询
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GoodsListQueryOption{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}
