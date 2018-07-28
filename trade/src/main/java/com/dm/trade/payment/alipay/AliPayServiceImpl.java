package com.dm.trade.payment.alipay;

import com.alipay.api.AlipayApiException;
import com.dm.trade.payment.AbstractOrderBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhongchao
 * @title AliPayServiceImpl.java
 * @Date 2017-12-12
 * @since v1.0.0
 */
@Service("aliPayService")
public class AliPayServiceImpl extends AbstractAliPayService {

//    @Value("${pay.alipay-response}")
    private String notifyUrl;

    private static final Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);

    @Override
    public String createOrderStr(AbstractOrderBean orderBean) throws AlipayApiException {
        return getRequestParams(orderBean, notifyUrl);
    }

    @Override
    public boolean asyncResopnse(HttpServletRequest request) {
        return super.asyncResopnse(request);
    }
}
