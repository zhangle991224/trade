package com.dm.trade.payment.weixin;

import java.io.Serializable;

/**
 * Created by Intellij idea
 * User: liu.y
 * Date: 2018/1/3
 * Description:
 * To change this template use File | Setting | File and Code Templates
 */
public class WeixinResponse implements Serializable {

    private String mch_id;
    private String out_trade_no;
    private String total_fee;
    private boolean tradestatus;
    private String tradedesc;

    public String getTradedesc() {
        return tradedesc;
    }

    public void setTradedesc(String tradedesc) {
        this.tradedesc = tradedesc;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public boolean isTradestatus() {
        return tradestatus;
    }

    public void setTradestatus(boolean tradestatus) {
        this.tradestatus = tradestatus;
    }

    @Override
    public String toString() {
        return "WeixinResponse{" +
                "mch_id='" + mch_id + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", tradestatus=" + tradestatus +
                ", tradedesc='" + tradedesc + '\'' +
                '}';
    }
}
