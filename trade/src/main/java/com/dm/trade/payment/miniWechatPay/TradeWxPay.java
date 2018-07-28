package com.dm.trade.payment.miniWechatPay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhongchao
 * @title TradeWxPay.java
 * @Date 2018-05-06
 * @since v1.0.0
 */
@Component("tradeWxPay")
public class TradeWxPay extends WxPay {

    @Value("${pay.trade.notifyUrl}")
    private String notifyUrl;

    public static final String BODY = "进货商品购买";

    public Map<String, Object> pay(String openId, String totalamount, String outTreadeNo, String ip) {
        return this.pay(openId, totalamount, outTreadeNo, ip, BODY, notifyUrl);
    }

    @Override
    public Map<String, Object> pay(String openId, String totalamount, String outTreadeNo, String ip, String body, String notify_url) {
        return super.pay(openId, totalamount, outTreadeNo, ip, body, notify_url);
    }
}
