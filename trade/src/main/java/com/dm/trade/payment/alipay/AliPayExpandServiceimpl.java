package com.dm.trade.payment.alipay;

import com.alipay.api.AlipayApiException;
import com.dm.trade.payment.AbstractOrderBean;
import com.dm.trade.payment.AbstractOrderBeanExpand;
import org.springframework.stereotype.Service;

/**
 * Created by Intellij idea
 * User: liu.y
 * Date: 2018/1/4
 * Description:
 * To change this template use File | Setting | File and Code Templates
 */
@Service(value = "alipayexpandService")
public class AliPayExpandServiceimpl extends AbstractAliPayService{

    @Override
    public String createOrderStr(AbstractOrderBean orderBean) throws AlipayApiException {
        AbstractOrderBeanExpand abstractOrderBeanExpand = (AbstractOrderBeanExpand) orderBean;
        return getRequestParams(orderBean, abstractOrderBeanExpand.getNotifyurl());
    }
}
