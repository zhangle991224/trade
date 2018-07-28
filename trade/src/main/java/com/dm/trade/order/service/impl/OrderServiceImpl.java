package com.dm.trade.order.service.impl;

import com.dm.trade.api.dto.Pager;
import com.dm.trade.api.dto.request.CommonPage;
import com.dm.trade.api.dto.request.order.OrderAddForm;
import com.dm.trade.api.dto.response.order.OrderCreateResult;
import com.dm.trade.api.dto.response.order.OrderDetailResult;
import com.dm.trade.api.dto.response.order.OrderResult;
import com.dm.trade.common.cache.CategoryBusinessCache;
import com.dm.trade.common.config.constant.OrderConstant;
import com.dm.trade.common.utils.BeanUtils;
import com.dm.trade.common.utils.MapHelper;
import com.dm.trade.customer.domain.CustomerAddressDO;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.customer.service.CustomerAddressService;
import com.dm.trade.goods.model.GoodsPriceModel;
import com.dm.trade.goods.service.GoodsService;
import com.dm.trade.order.IdGenner;
import com.dm.trade.order.bean.OrderListBean;
import com.dm.trade.order.dao.OrderCartDao;
import com.dm.trade.order.dao.OrderDao;
import com.dm.trade.order.dao.OrderDetailDao;
import com.dm.trade.order.domain.OrderCartDO;
import com.dm.trade.order.domain.OrderDO;
import com.dm.trade.order.domain.OrderDetailDO;
import com.dm.trade.order.service.OrderDetailService;
import com.dm.trade.order.service.OrderService;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    public static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Value("${pay.needpay}")
    private Integer needPay;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderCartDao orderCartDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CustomerAddressService addressService;

    @Autowired
    private CategoryBusinessCache businessCache;

    @Override
    public OrderDO get(Long id) {
        return orderDao.get(id);
    }

    @Override
    public List<OrderDO> list(Map<String, Object> map) {
        return orderDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return orderDao.count(map);
    }

    @Override
    public int save(OrderDO order) {
        return orderDao.save(order);
    }

    @Override
    public int update(OrderDO order) {
        return orderDao.update(order);
    }

    @Override
    public int remove(Integer id) {
        return orderDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return orderDao.batchRemove(ids);
    }

    @Override
    public List<OrderListBean> listbk(Map<String, Object> map) {
        List<OrderDO> list = orderDao.list(map);
        List<OrderListBean> collect = list.stream().map(e -> {
            OrderListBean orderResult = BeanUtils.copyProperties(e, OrderListBean.class);
            List<OrderDetailResult> orderDetails = orderDetailService.getOrderDetails(e.getId());
            CustomerAddressDO customerAddressDO = addressService.get(e.getAddressId());
            orderResult.setCustomerAddressDO(customerAddressDO);
            orderResult.setOrderDetailResults(orderDetails);
            return orderResult;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public OrderCreateResult createOrderAndValid(OrderAddForm orderAddForm, CustomerDO customerDO) {
        // 获取商品价格
        List<Long> ids = orderAddForm.getIds();
        List<OrderCartDO> cartList = orderCartDao.getByIds(ids);
        if (CollectionUtils.isEmpty(cartList)) {
            logger.error("未获取到购物车信息! request params:[{}]", orderAddForm.toString());
            throw new IllegalArgumentException("未获取到购物车信息");
        }
        List<Long> goodsIdList = cartList.stream().map(OrderCartDO::getGoodsId).collect(Collectors.toList());
        Map<Long, GoodsPriceModel> goodsListRealPrice = goodsService.getGoodsListRealPrice(goodsIdList, customerDO);
        Integer price = moneyValidate(orderAddForm, cartList, goodsListRealPrice);
        Map<Integer, List<OrderCartDO>> businessMap = cartList.stream().collect(Collectors.groupingBy(OrderCartDO::getVar02));
        Map<OrderDO, List<OrderDetailDO>> orderMap = Maps.newHashMap();
        // 根据供应商 拆单
        businessMap.forEach((bid, v) -> {
            // 订单下的商品金额
            Integer orderCartMoney = getOrderCartMoney(v, goodsListRealPrice);
            // 开始创建订单
            OrderDO orderDO = BeanUtils.copyProperties(orderAddForm, OrderDO.class);
            orderDO.setId(IdGenner.genOrdId16());
            orderDO.setIsPaid(OrderConstant.PAID_NO);
            orderDO.setCustomerId(customerDO.getId());
            orderDO.setStatus(OrderConstant.ORDER_STATUS_CREATE);
            orderDO.setTotalmoney(orderCartMoney);
            orderDO.setVar02(bid);
            if (OrderConstant.NOT_NEED_PAY.equals(needPay)) {
                orderDO.setPayType(OrderConstant.OFFLINE_PAY);
            }
//        orderDO.setVar01(deposit);
            // 购物车
            List<OrderDetailDO> orderDetailDOS = genOrderDetail(customerDO, v, goodsListRealPrice);

            orderMap.put(orderDO, orderDetailDOS);
        });
        return this.createOrderWithOutValid(orderMap, ids.toArray(new Long[0]));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderCreateResult createOrderWithOutValid(Map<OrderDO, List<OrderDetailDO>> orderMap, Long... cartIds) {
        List<Long> orderIds = Lists.newArrayList();
        Assert.notNull(orderMap, "订单参数错误!");
        orderMap.forEach((orderDO, orderDetailDOList) -> {
            orderDao.save(orderDO);
            orderDetailDOList.forEach(e -> e.setOrderId(orderDO.getId()));
            orderDetailDao.batchSave(orderDetailDOList);
            orderIds.add(orderDO.getId());
            if (cartIds != null) {
                orderCartDao.batchRemove(cartIds);
            }
        });

        return new OrderCreateResult(orderIds, needPay);
    }

    @Override
    public Pager<OrderResult> getOrderList(CommonPage page, Long customerId) {
        Map<String, Object> params = new MapHelper<>().putBeansToMap(page);
        params.put("customerId", customerId);
        int count = orderDao.count(params);
        if (count > 0) {
            List<OrderDO> list = orderDao.list(params);
            List<OrderResult> collect = list.stream().map(e -> {
                OrderResult orderResult = BeanUtils.copyProperties(e, OrderResult.class);
                List<OrderDetailResult> orderDetails = orderDetailService.getOrderDetails(e.getId());
                orderResult.setOrderDetailResults(orderDetails);
                return orderResult;
            }).collect(Collectors.toList());
            return new Pager<>(collect, count);
        }
        return new Pager<>();
    }

    /**
     * 订单金额校验
     *
     * @param orderAddForm
     * @param cartList
     * @param goodsListRealPrice
     */
    private Integer moneyValidate(OrderAddForm orderAddForm, List<OrderCartDO> cartList, Map<Long, GoodsPriceModel> goodsListRealPrice) {
        Integer price = 0;
        Integer deposit = 0;
        for (OrderCartDO orderCartDO : cartList) {
            GoodsPriceModel goodsPriceModel = goodsListRealPrice.get(orderCartDO.getGoodsId());
            if (orderCartDO.getUnit().equals(OrderConstant.UNIT_BULK)) {
                price += (goodsPriceModel.getBulkPrice() * orderCartDO.getNum());
            } else {
                price += (goodsPriceModel.getContainerPrice() * orderCartDO.getNum());
            }
            deposit += (goodsPriceModel.getVar01() * orderCartDO.getNum());
        }
        if (!price.equals(orderAddForm.getOrderMoney())) {
            throw new IllegalArgumentException("创建订单失败! 参数错误 订单实际金额:" + price + " 传入金额:" + orderAddForm.getPayMoney());
        }
        return price;
    }

    /**
     * 订单金额校验
     *
     * @param cartList
     * @param goodsListRealPrice
     */
    private Integer getOrderCartMoney(List<OrderCartDO> cartList, Map<Long, GoodsPriceModel> goodsListRealPrice) {
        Integer price = 0;
        for (OrderCartDO orderCartDO : cartList) {
            GoodsPriceModel goodsPriceModel = goodsListRealPrice.get(orderCartDO.getGoodsId());
            if (orderCartDO.getUnit().equals(OrderConstant.UNIT_BULK)) {
                price += (goodsPriceModel.getBulkPrice() * orderCartDO.getNum());
            } else {
                price += (goodsPriceModel.getContainerPrice() * orderCartDO.getNum());
            }
        }
        return price;
    }

    /**
     * 生成订单详情
     *
     * @param customerDO
     * @param cartList
     * @param goodsListRealPrice
     * @return
     */
    private List<OrderDetailDO> genOrderDetail(CustomerDO customerDO, List<OrderCartDO> cartList, Map<Long, GoodsPriceModel> goodsListRealPrice) {
        return cartList.stream().map(e -> {
            OrderDetailDO orderDetailDO = new OrderDetailDO();
            GoodsPriceModel goodsPriceModel = goodsListRealPrice.get(e.getGoodsId());
            orderDetailDO.setGoodsId(e.getGoodsId());
            orderDetailDO.setCustomerId(customerDO.getId());
            orderDetailDO.setNum(e.getNum());
            orderDetailDO.setUnit(e.getUnit());
            Integer price;
            if (e.getUnit().equals(OrderConstant.UNIT_BULK)) {
                price = goodsPriceModel.getBulkPrice();
            } else {
                price = goodsPriceModel.getContainerPrice();
            }
            orderDetailDO.setTotalPrice(e.getNum() * price);
            if (goodsPriceModel.isActivity()) {
                orderDetailDO.setActivityPrice(price);
            }
            orderDetailDO.setUnitPrice(price);
            return orderDetailDO;
        }).collect(Collectors.toList());
    }


}
