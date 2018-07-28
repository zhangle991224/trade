package com.dm.trade.order.service;

import com.dm.trade.api.dto.Pager;
import com.dm.trade.api.dto.request.CommonPage;
import com.dm.trade.api.dto.request.order.OrderAddForm;
import com.dm.trade.api.dto.response.order.OrderCreateResult;
import com.dm.trade.api.dto.response.order.OrderResult;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.order.bean.OrderListBean;
import com.dm.trade.order.domain.OrderDO;
import com.dm.trade.order.domain.OrderDetailDO;

import java.util.List;
import java.util.Map;

/**
 * 订单表
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-15 15:53:40
 */
public interface OrderService {

    OrderDO get(Long id);

    List<OrderDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(OrderDO order);

    int update(OrderDO order);

    int remove(Integer id);

    int batchRemove(Integer[] ids);

    /**
     * 重写后台管理端获取订单列表
     *
     * @param map
     * @return
     */
    List<OrderListBean> listbk(Map<String, Object> map);

    /**
     * 创建订单
     * <p>创建完成订单后 删除购物车信息</p>
     *
     * @param orderAddForm
     * @param customerDO   用户信息
     * @return
     */
    OrderCreateResult createOrderAndValid(OrderAddForm orderAddForm, CustomerDO customerDO);

    /**
     * 创建订单 不做校验
     * <p> 事务层</p>
     *
     * @param orderMap 订单map k:order v:detail
     * @param cartIds  购物车id
     * @return
     */
    OrderCreateResult createOrderWithOutValid(Map<OrderDO, List<OrderDetailDO>> orderMap, Long... cartIds);

    /**
     * 获取当前用户的所有订单列表
     *
     * @param customerId
     * @return
     */
    Pager<OrderResult> getOrderList(CommonPage page, Long customerId);
}
