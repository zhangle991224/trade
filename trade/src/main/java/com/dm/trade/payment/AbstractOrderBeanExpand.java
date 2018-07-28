package com.dm.trade.payment;

/**
 * Created by Intellij idea
 * User: liu.y
 * Date: 2018/1/4
 * Description:
 * To change this template use File | Setting | File and Code Templates
 */
public class AbstractOrderBeanExpand extends AlipayOrderBean{

    private String notifyurl; //自定义回调

    public String getNotifyurl() {
        return notifyurl;
    }

    public void setNotifyurl(String notifyurl) {
        this.notifyurl = notifyurl;
    }
}
