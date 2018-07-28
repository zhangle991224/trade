package com.dm.trade.api;

import com.dm.trade.api.dto.APIResult;
import com.dm.trade.api.dto.request.CommonPage;
import com.dm.trade.api.dto.request.order.OrderAddForm;
import com.dm.trade.api.dto.request.order.OrderPayForm;
import com.dm.trade.api.dto.response.order.OrderCreateResult;
import com.dm.trade.common.annotation.NeedLogin;
import com.dm.trade.common.config.constant.OrderConstant;
import com.dm.trade.common.utils.HttpContextUtils;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.order.domain.OrderDO;
import com.dm.trade.order.service.OrderDetailService;
import com.dm.trade.order.service.OrderService;
import com.dm.trade.payment.miniWechatPay.IPUtils;
import com.dm.trade.payment.miniWechatPay.TradeWxPay;
import com.dm.trade.payment.weixin.WeixinResponse;
import org.apache.http.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * @author zhongchao
 * @title APIOrderController.java
 * @Date 2018-04-15
 * @since v1.0.0
 */

@RestController
@RequestMapping("/api/order")
public class APIOrderController {

    public static final Logger logger = LoggerFactory.getLogger(APIOrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Resource
    private TradeWxPay tradeWxPay;

    /**
     * 创建订单
     *
     * @param form
     * @return
     */
    @PostMapping("create")
    public APIResult createOrder(@Valid OrderAddForm form, HttpServletRequest request) {
        CustomerDO customerInfo = HttpContextUtils.getCustomerInfo();
        OrderCreateResult orderAndValid = orderService.createOrderAndValid(form, customerInfo);
        return APIResult.isOk(orderAndValid);
    }

    /**
     * 获取用户订单列表
     *
     * @param page
     * @return
     */
    @NeedLogin
    @RequestMapping("list")
    public APIResult getOrderList(CommonPage page) {
        CustomerDO customerInfo = HttpContextUtils.getCustomerInfo();
        return APIResult.isOk(orderService.getOrderList(page, customerInfo.getId()));
    }

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    @NeedLogin
    @RequestMapping("/detail")
    public APIResult getOrderDetail(Long orderId) {
        return APIResult.isOk(orderDetailService.getOrderDetails(orderId));
    }

    @RequestMapping("/pay")
    public APIResult pay(@Valid OrderPayForm form, HttpServletRequest request) {
        CustomerDO customerInfo = HttpContextUtils.getCustomerInfo();
        OrderDO orderDO = orderService.get(form.getOrderId());
        Assert.notNull(orderDO, "订单不存在!");
        Map<String, Object> pay = tradeWxPay.pay(customerInfo.getOpenId(), orderDO.getTotalmoney().toString(), form.getOrderId().toString(), IPUtils.getClientIP(request));
        return APIResult.isOk(pay);
    }

    @RequestMapping("/wxdtnotify")
    public void weiPayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {

        WeixinResponse weixinResponse = tradeWxPay.asyncResopnse(request, response);
        if (weixinResponse.isTradestatus()) {
            String out_trade_no = weixinResponse.getOut_trade_no();
            OrderDO orderDO = new OrderDO();
            orderDO.setId(Long.valueOf(out_trade_no));
            orderDO.setStatus(OrderConstant.ORDER_STATUS_WAIT_SEND);
            orderDO.setIsPaid(OrderConstant.PAID);
            orderService.update(orderDO);
            response.getOutputStream().print("SUCCESS");
        } else {
            logger.error("微信支付异步回调校验失败! 原因:[{}]", weixinResponse.toString());

        }
    }
}
