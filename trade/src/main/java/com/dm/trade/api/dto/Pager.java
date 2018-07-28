package com.dm.trade.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * api 统一的page返回对象
 *
 * @author zhongchao
 */
public class Pager<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    // 总记录数
    private int total;
    // 列表数据
    private List<T> rows;

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public Pager(List<T> list, int total) {
        this.rows = list;
        this.total = total;
    }

    public Pager(List<T> list) {
        this.rows = list;
        this.total = list.size();
    }

    public Pager() {
        this.total = 0;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

}
