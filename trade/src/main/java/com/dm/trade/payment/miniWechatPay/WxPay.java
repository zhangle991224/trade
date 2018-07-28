package com.dm.trade.payment.miniWechatPay;

import com.alibaba.fastjson.JSON;
import com.dm.trade.common.exception.PayException;
import com.dm.trade.common.utils.StringUtils;
import com.dm.trade.common.wx.ConfigUtil;
import com.dm.trade.payment.weixin.PayCommonUtil;
import com.dm.trade.payment.weixin.WeixinResponse;
import com.dm.trade.payment.weixin.XMLUtil;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * description: 微信支付相关
 * Created by zhangle on 2016/12/22.
 * qq:475166369
 */
@Component
public class WxPay extends BaseApi {
    public static final Logger logger = LoggerFactory.getLogger(WxPay.class);

    /**
     * 微信统一下单
     *
     * @param totalamount 金额分
     * @param outTreadeNo
     * @return
     */
    public Map<String, Object> pay(String openId, String totalamount, String outTreadeNo, String ip, String body, String notify_url) {
        try {
            //金额，元转换成分
            BigDecimal bd = new BigDecimal(totalamount);
            bd = bd.setScale(2, BigDecimal.ROUND_UNNECESSARY);

            int total = bd.multiply(new BigDecimal("100")).intValue();
            //IP
//            String IP = IPUtils.getClientIP(request);
            //生成随机字符串
            String nonceStr = AesCipher.generateSecretKey();

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("appid", ConfigUtil.appid);
            paramMap.put("mch_id", ConfigUtil.mch_id);

//            paramMap.put("sub_appid", Constant.APP_ID);
//            paramMap.put("sub_mch_id", Constant.MCH_ID);

            paramMap.put("device_info", ConfigUtil.DEVICE_INFO);
            paramMap.put("body", body);
            paramMap.put("nonce_str", nonceStr);
            paramMap.put("out_trade_no", outTreadeNo);
            paramMap.put("total_fee", totalamount);
            paramMap.put("spbill_create_ip", ip);
            paramMap.put("notify_url", notify_url);
            paramMap.put("trade_type", "JSAPI");
            paramMap.put("openid", openId);
//            paramMap.put("limit_pay", "no_credit");
            //生成签名
            String sign = WechatUtils.generateSign(paramMap);
            //调用微信下单接口
            paramMap.put("sign", sign);
            Map<String, Object> resultPay = unifiedorder(paramMap);
            logger.info("微信统一下单接口返回====>" + resultPay.get("return_code") + "," + resultPay.get("return_msg"));
            if ("SUCCESS".equals(resultPay.get("return_code")) && "SUCCESS".equals(resultPay.get("result_code"))) {
                String prepayId = (String) resultPay.get("prepay_id");
                String ns = AesCipher.generateSecretKey();
                //返回给小程序的参数
                Map<String, Object> paymentMap = new HashMap<>();
                paymentMap.put("appId", ConfigUtil.appid);
                paymentMap.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
                paymentMap.put("nonceStr", ns);
                paymentMap.put("package", "prepay_id=" + prepayId);
                paymentMap.put("signType", "MD5");
                //生成签名
                String signBack = WechatUtils.generateSign(paymentMap);
                paymentMap.put("paySign", signBack);

                return paymentMap;
            } else {
                logger.info("微信统一下单失败====>" + resultPay.get("err_code") + "," + resultPay.get("err_code_des"));
                throw new PayException("微信统一下单失败");
            }
        } catch (Exception e) {
            logger.info("微信统一下单失败====>", e);
            throw new PayException("微信统一下单失败", e);
        }
    }

    /**
     * 微信一下单
     *
     * @return
     * @throws Exception
     */
    public Map<String, Object> unifiedorder(Map<String, Object> paramMap) throws Exception {
        StringBuffer xml = new StringBuffer();
        xml.append("<xml>");
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (entry.getValue() != null && !StringUtils.isEmpty(entry.getValue().toString())) {
                xml.append("<").append(entry.getKey()).append("><![CDATA[").append(entry.getValue()).append("]]></").append(entry.getKey()).append(">");
            }
        }
        xml.append("</xml>");
        String result = this.sendPostHttp("https://api.mch.weixin.qq.com/pay/unifiedorder", xml.toString());
        logger.info("微信下单返回====>" + result);
        return XmlUtils.xmlBody2map(result, "xml");
    }

    /**
     * 微信退款
     *
     * @param transactionId
     * @param outRefundNo
     * @param totalFee
     * @param refundFee
     * @return
     * @throws Exception
     * @throws RuntimeException
     */
    public Map<String, Object> refund(String transactionId, String outRefundNo, int totalFee, int refundFee) throws Exception, RuntimeException {
        //生成随机字符串
        String nonceStr = AesCipher.generateSecretKey();
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("appid", ConfigUtil.getContext("app.appid"));
//        paramMap.put("mch_id", Constant.MCH_ID);

        paramMap.put("appid", ConfigUtil.appid);
        paramMap.put("mch_id", ConfigUtil.mch_id);
////
//        paramMap.put("sub_appid", Constant.APP_ID);
//        paramMap.put("sub_mch_id", Constant.MCH_ID);

        paramMap.put("device_info", ConfigUtil.DEVICE_INFO);
        paramMap.put("nonce_str", nonceStr);
        paramMap.put("transaction_id", transactionId);
        paramMap.put("out_refund_no", outRefundNo);
        paramMap.put("total_fee", totalFee);
        paramMap.put("refund_fee", refundFee);
        paramMap.put("refund_fee_type", "authorization_code");
        paramMap.put("op_user_id", ConfigUtil.mch_id);
        //生成签名
        String sign = WechatUtils.generateSign(paramMap);
        paramMap.put("sign", sign);
        String result = this.sendPostHttps("https://api.mch.weixin.qq.com/secapi/pay/refund", XmlUtils.map2xmlBody(paramMap, "xml"));
        this.logger.info("退款通知====》" + result);
        Map<String, Object> map = XmlUtils.xmlBody2map(result, "xml");
        return map;
    }

    /**
     * 查询微信订单状态
     *
     * @param transaction_id
     * @param out_trade_no
     * @return
     * @throws Exception
     */
    public Map<String, Object> orderquery(String transaction_id, String out_trade_no) throws Exception {
        //生成随机字符串
        String nonceStr = AesCipher.generateSecretKey();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("appid", ConfigUtil.appid);
        paramMap.put("mch_id", ConfigUtil.mch_id);

//        paramMap.put("sub_appid", Constant.APP_ID);
//        paramMap.put("sub_mch_id", Constant.MCH_ID);

        paramMap.put("transaction_id", transaction_id);
        paramMap.put("out_trade_no", out_trade_no);
        paramMap.put("nonce_str", nonceStr);
        //生成签名
        String sign = WechatUtils.generateSign(paramMap);
        paramMap.put("sign", sign);
        String result = this.sendPostHttp("https://api.mch.weixin.qq.com/pay/orderquery", XmlUtils.map2xmlBody(paramMap, "xml"));
        Map<String, Object> map = XmlUtils.xmlBody2map(result, "xml");
        return map;
    }

    public WeixinResponse asyncResopnse(HttpServletRequest request, HttpServletResponse response) {
        WeixinResponse weixinResponse = new WeixinResponse();

        try {
            //读取参数
            InputStream inputStream;
            StringBuffer sb = new StringBuffer();
            inputStream = request.getInputStream();
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
            in.close();
            inputStream.close();
            //解析xml成map
            SortedMap<String, Object> m = XMLUtil.doXMLParse(sb.toString());

            //过滤空 设置 TreeMap
            SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
            Iterator it = m.keySet().iterator();
            while (it.hasNext()) {
                String parameter = (String) it.next();
                String parameterValue = String.valueOf(m.get(parameter));

                String v = "";
                if (null != parameterValue) {
                    v = parameterValue.trim();
                }
                packageParams.put(parameter, v);
            }
            logger.info("微信支付异步回调参数->[{}]", JSON.toJSONString(packageParams));
            //判断签名是否正确
            if (PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
                if ("SUCCESS".equals(packageParams.get("result_code"))) {
                    // 这里是支付成功 返回respnose
                    String mch_id = (String) packageParams.get("mch_id"); //商户号
                    String out_trade_no = (String) packageParams.get("out_trade_no"); //商户订单号
                    String total_fee = (String) packageParams.get("total_fee");
                    weixinResponse.setMch_id(mch_id);
                    weixinResponse.setOut_trade_no(out_trade_no);
                    weixinResponse.setTotal_fee(total_fee);
                    weixinResponse.setTradestatus(true);
                } else {
                    weixinResponse.setTradestatus(false);
                    weixinResponse.setTradedesc("支付失败");
                }


            } else {
                weixinResponse.setTradestatus(false);
                weixinResponse.setTradedesc("通知签名验证失败");
            }
        } catch (IOException e) {
            weixinResponse.setTradestatus(false);
        } catch (JDOMException e) {
            weixinResponse.setTradestatus(false);
        }
        return weixinResponse;
    }
}
