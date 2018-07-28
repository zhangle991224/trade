//package com.hmcq.payment.weixin;
//
//import com.dm.trade.party.domain.OrderDO;
//import com.dm.trade.payment.WeixinPayService;
//import org.jdom.JDOMException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.math.BigDecimal;
//import java.util.Iterator;
//import java.util.SortedMap;
//import java.util.TreeMap;
//
///**
// * Created by Intellij idea
// * User: liu.y
// * Date: 2018/1/3
// * Description:
// * To change this template use File | Setting | File and Code Templates
// */
//@Service("weixinPayService")
//public class WeixinPayServiceImpl implements WeixinPayService{
//
//
//    @Override
//    public String createDoOrder(HttpServletRequest request, OrderDO orderDO) {
//        BigDecimal price = orderDO.getMoney();
//        int price100 = price.multiply(new BigDecimal(100)).intValue();
//
//        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
//        parameters.put("appid", ConfigUtil.APPID);
//        parameters.put("mch_id", ConfigUtil.MCH_ID);
//        parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
//        parameters.put("body", request.getAttribute("paybody"));
//        String name = (String) request.getAttribute("name"); //获取支付类型：爱心驿站、爱心食堂
//        parameters.put("out_trade_no", name + String.valueOf(orderDO.getId())); //订单id
//        parameters.put("fee_type", "CNY");
//        parameters.put("total_fee", String.valueOf(price100));
//        parameters.put("spbill_create_ip",request.getRemoteAddr());
//        parameters.put("notify_url", request.getAttribute("partynotifyUrl"));
//        parameters.put("trade_type", "APP");
//        //设置签名
//        String sign = PayCommonUtil.createSign("UTF-8",parameters);
//        parameters.put("sign", sign);
//        //封装请求参数结束
//        String requestXML = PayCommonUtil.getRequestXml(parameters);
//        //调用统一下单接口
//        String result = PayCommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
//        return result;
//    }
//
//    @Override
//    public SortedMap<Object, Object> buildOderParam(String result) {
//        SortedMap<Object, Object> parameterMap2 = null;
//
//        try {
//            SortedMap<String, Object> map = XMLUtil.doXMLParse(result);
//            parameterMap2 = new TreeMap<>();
//            parameterMap2.put("appid", ConfigUtil.APPID);
//            parameterMap2.put("partnerid", ConfigUtil.MCH_ID);
//            parameterMap2.put("prepayid", map.get("prepay_id"));
//            parameterMap2.put("package", "Sign=WXPay");
//            parameterMap2.put("noncestr", PayCommonUtil.CreateNoncestr());
//            //本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
//            parameterMap2.put("timestamp", Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0,10)));
//            String sign2 = PayCommonUtil.createSign("UTF-8",parameterMap2);
//            parameterMap2.put("sign", sign2);
//        } catch (JDOMException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return parameterMap2;
//    }
//
//    @Override
//    public WeixinResponse asyncResopnse(HttpServletRequest request, HttpServletResponse response) {
//        WeixinResponse weixinResponse = new WeixinResponse();
//
//        try {
//            //读取参数
//            InputStream inputStream ;
//            StringBuffer sb = new StringBuffer();
//            inputStream = request.getInputStream();
//            String s ;
//            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//            while ((s = in.readLine()) != null){
//                sb.append(s);
//            }
//            in.close();
//            inputStream.close();
//            //解析xml成map
//            SortedMap<String, Object> m =  XMLUtil.doXMLParse(sb.toString());
//
//            //过滤空 设置 TreeMap
//            SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
//            Iterator it = m.keySet().iterator();
//            while (it.hasNext()) {
//                String parameter = (String) it.next();
//                String parameterValue = String.valueOf(m.get(parameter));
//
//                String v = "";
//                if(null != parameterValue) {
//                    v = parameterValue.trim();
//                }
//                packageParams.put(parameter, v);
//            }
//
//            //判断签名是否正确
//            if(PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
//                if("SUCCESS".equals(packageParams.get("result_code"))){
//                    // 这里是支付成功 返回respnose
//                    String mch_id = (String)packageParams.get("mch_id"); //商户号
//                    String out_trade_no = (String)packageParams.get("out_trade_no"); //商户订单号
//                    String total_fee = (String)packageParams.get("total_fee");
//                    weixinResponse.setMch_id(mch_id);
//                    weixinResponse.setOut_trade_no(out_trade_no);
//                    weixinResponse.setTotal_fee(total_fee);
//                    weixinResponse.setTradestatus(true);
//                }else {
//                    weixinResponse.setTradestatus(false);
//                    weixinResponse.setTradedesc("支付失败");
//                }
//
//
//            } else{
//                weixinResponse.setTradestatus(false);
//                weixinResponse.setTradedesc("通知签名验证失败");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            weixinResponse.setTradestatus(false);
//        } catch (JDOMException e) {
//            e.printStackTrace();
//            weixinResponse.setTradestatus(false);
//        }
//        return weixinResponse;
//    }
//}
