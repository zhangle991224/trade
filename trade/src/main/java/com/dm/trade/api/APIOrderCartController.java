package com.dm.trade.api;

import com.dm.trade.api.dto.APIResult;
import com.dm.trade.api.dto.Pager;
import com.dm.trade.api.dto.request.order.OrderCartForm;
import com.dm.trade.api.dto.request.order.OrderCartQueryOption;
import com.dm.trade.common.annotation.NeedLogin;
import com.dm.trade.common.utils.HttpContextUtils;
import com.dm.trade.common.utils.PageUtils;
import com.dm.trade.common.utils.R;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.order.service.OrderCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author zhongchao
 * @title APIOrderController.java
 * @Date 2018-04-15
 * @since v1.0.0
 */
@RestController
@RequestMapping("/api/order/cart")
public class APIOrderCartController {

    @Autowired
    private OrderCartService orderCartService;

    /**
     * 增加购物车
     *
     * @return
     */
    @NeedLogin
    @RequestMapping("add")
    public R addOrderCart(@Valid OrderCartForm form) {
        CustomerDO customerInfo = HttpContextUtils.getCustomerInfo();
        orderCartService.addOrderCart(form, customerInfo.getId());
        return R.ok();
    }

    /**
     * 获取购物车列表
     *
     * @param queryOption
     * @return
     */
    @NeedLogin
    @RequestMapping("list")
    public APIResult queryOrderCart(OrderCartQueryOption queryOption) {
        CustomerDO customerInfo = HttpContextUtils.getCustomerInfo();
        Pager pageUtils = orderCartService.queryOrderCartPage(queryOption, customerInfo);
        return APIResult.isOk(pageUtils);
    }

    /**
     * 删除购物车
     *
     * @param id
     * @return
     */
    @RequestMapping("del")
    public R delOrderCart(Long id) {
        orderCartService.remove(id.intValue());
        return R.ok();
    }
}
