package com.dm.trade.payment;

import com.alipay.api.AlipayApiException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhongchao
 * @title IPayService.java
 * @Date 2017-12-12
 * @since v1.0.0
 */
public interface IPayService {

    /**
     * 创建验签信息
     *
     * @return
     */
    String createOrderStr(AbstractOrderBean orderBean) throws AlipayApiException;

    /**
     * 异步回调
     *
     * @param request
     * @return
     */
    boolean asyncResopnse(HttpServletRequest request);
}
