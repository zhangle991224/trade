package com.dm.trade.order.service;

import com.dm.trade.api.dto.Pager;
import com.dm.trade.api.dto.request.order.OrderCartForm;
import com.dm.trade.api.dto.request.order.OrderCartQueryOption;
import com.dm.trade.api.dto.response.order.OrderCartResult;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.order.domain.OrderCartDO;

import java.util.List;
import java.util.Map;

/**
 * 购物车
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-15 15:53:41
 */
public interface OrderCartService {

    OrderCartDO get(Integer id);

    List<OrderCartDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(OrderCartDO orderCart);

    int update(OrderCartDO orderCart);

    int remove(Integer id);

    int batchRemove(Long[] ids);

    /**
     * 增加商品到购物车
     * <p>如果发现商品在该用户中存在 则做数量累加</p>
     *
     * @param form
     */
    void addOrderCart(OrderCartForm form, Long customerId);

    /**
     * 获取用户购物车信息
     *
     * @param queryOption
     * @return
     */
    Pager<OrderCartResult> queryOrderCartPage(OrderCartQueryOption queryOption, CustomerDO customerDO);
}
