package com.dm.trade.payment.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.json.JSONWriter;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.google.common.collect.Maps;
import com.dm.trade.payment.AbstractOrderBean;
import com.dm.trade.payment.IPayService;
import com.dm.trade.payment.alipay.utils.AlipayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhongchao
 * @title AbstractAliPayService.java
 * @Date 2018-01-03
 * @since v1.0.0
 */
public abstract class AbstractAliPayService implements IPayService {

    private static final Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);


    @Override
    public boolean asyncResopnse(HttpServletRequest request) {
        boolean success = false;
        Map<String, String> params = getRespnseParams(request);
        logger.info("支付宝异步回调:参数:{}", JSON.toJSONString(params));
        try {
            if (AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, "UTF-8", AlipayConfig.SIGN_TYPE)) {
                logger.debug("--------------------支付宝异步回调验签成功-------------------");
                String tradeStatus = params.get(AlipayConfig.TRADE_STATUS);
                if (tradeStatus.equals(TradStatusEnum.TRADE_SUCCESS.name()) || tradeStatus.equals(TradStatusEnum.TRADE_FINISHED.name())) {
                    logger.info("支付成功.");
                    success = true;
                }
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝验签失败!", e);
            return false;
        }
        return success;
    }

    /**
     * 获取请求参数对象
     *
     * @return
     */
    String getRequestParams(AbstractOrderBean orderBean, String notifyUrl) throws AlipayApiException {
        AlipayClient alipayClient = AlipayUtils.buildClient();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl(notifyUrl);
        JSONWriter json = new JSONWriter();
        request.setBizContent(json.write(orderBean));

        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        return response.getBody();
    }

    /**
     * 处理回调参数
     *
     * @param request
     * @return
     */
    Map<String, String> getRespnseParams(HttpServletRequest request) {
        Map<String, String> map = Maps.newHashMap();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String name : parameterMap.keySet()) {
            String[] values = parameterMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            map.put(name, valueStr);
        }
        return map;
    }
}
