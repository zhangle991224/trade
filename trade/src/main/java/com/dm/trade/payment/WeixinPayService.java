//package com.hmcq.payment;
//
//import com.dm.trade.party.domain.OrderDO;
//import com.dm.trade.payment.weixin.WeixinResponse;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.SortedMap;
//
///**
// * Created by Intellij idea
// * User: liu.y
// * Date: 2018/1/3
// * Description:
// * To change this template use File | Setting | File and Code Templates
// */
//public interface WeixinPayService {
//
//    /**
//     * 调用统一下单接口
//     * @param request
//     * @param orderDO
//     * @return
//     */
//    String createDoOrder(HttpServletRequest request, OrderDO orderDO);
//
//    /**
//     * 统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。
//     * 参与签名的字段名为appId，partnerId，prepayId，nonceStr，timeStamp，package。
//     * 注意：package的值格式为Sign=WXPay
//     * @param result
//     * @return
//     */
//    SortedMap<Object, Object> buildOderParam(String result);
//
//
//    /**
//     * 异步回调
//     *
//     * @param request
//     * @return
//     */
//    WeixinResponse asyncResopnse(HttpServletRequest request, HttpServletResponse response);
//}
