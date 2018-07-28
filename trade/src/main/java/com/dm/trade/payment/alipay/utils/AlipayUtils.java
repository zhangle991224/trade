package com.dm.trade.payment.alipay.utils;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.dm.trade.payment.alipay.AlipayConfig;

/**
 * Created by Intellij idea
 * User: liu.y
 * Date: 2017/12/12
 * Description:
 * To change this template use File | Setting | File and Code Templates
 */
public class AlipayUtils {


    public static AlipayClient buildClient(){
        AlipayClient alipayClient = new DefaultAlipayClient(
                AlipayConfig.URL,
                AlipayConfig.APP_ID,
                AlipayConfig.APP_PRIVATE_KEY,
                AlipayConfig.FORMAT,
                AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.SIGN_TYPE);
        return alipayClient;

    }


}
