package com.dm.trade.order.service.impl;

import com.dm.trade.api.dto.Pager;
import com.dm.trade.api.dto.request.order.OrderCartForm;
import com.dm.trade.api.dto.request.order.OrderCartQueryOption;
import com.dm.trade.api.dto.response.order.OrderCartResult;
import com.dm.trade.common.cache.CategoryBusinessCache;
import com.dm.trade.common.config.constant.OrderConstant;
import com.dm.trade.common.utils.BeanUtils;
import com.dm.trade.common.utils.MapHelper;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.goods.dao.GoodsDao;
import com.dm.trade.goods.domain.GoodsDO;
import com.dm.trade.goods.model.GoodsPriceModel;
import com.dm.trade.goods.service.GoodsService;
import com.dm.trade.order.dao.OrderCartDao;
import com.dm.trade.order.domain.OrderCartDO;
import com.dm.trade.order.service.OrderCartService;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class OrderCartServiceImpl implements OrderCartService {
    @Autowired
    private OrderCartDao orderCartDao;

    @Autowired
    private GoodsDao goodsDao;


    @Value("${app.local.host}")
    private String localHost;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryBusinessCache businessCache;

    @Override
    public OrderCartDO get(Integer id) {
        return orderCartDao.get(id);
    }

    @Override
    public List<OrderCartDO> list(Map<String, Object> map) {
        return orderCartDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return orderCartDao.count(map);
    }

    @Override
    public int save(OrderCartDO orderCart) {
        return orderCartDao.save(orderCart);
    }

    @Override
    public int update(OrderCartDO orderCart) {
        return orderCartDao.update(orderCart);
    }

    @Override
    public int remove(Integer id) {
        return orderCartDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return orderCartDao.batchRemove(ids);
    }

    @Override
    public void addOrderCart(OrderCartForm form, Long customerId) {
        cartParamsVali(form);
        /* 获取当前用户、商品、单位的数据 如果不存在 则直接新增，否则做数量累加*/
        // 散装
        if (form.getBulkNum() != null && form.getBulkNum() > 0) {
            OrderCartDO orderCartDO = this.getCustomerOrderCart(customerId, form.getGoodsId(), OrderConstant.UNIT_BULK);
            // 如果该客户添加过该商品到购物车，则直接覆盖原有商品的单价和数量
            if (orderCartDO != null) {
                this.updateOrderCart(orderCartDO, form.getBulkPrice(), form.getBulkNum());
            } else {
                this.addOrderCart(form, OrderConstant.UNIT_BULK, customerId);
            }
        }
        if (form.getContainerNum() != null && form.getContainerNum() > 0) {
            OrderCartDO orderCartDO = this.getCustomerOrderCart(customerId, form.getGoodsId(), OrderConstant.UNIT_CONTAINER);
            // 如果该客户添加过该商品到购物车，则直接覆盖原有商品的单价和数量
            if (orderCartDO != null) {
                this.updateOrderCart(orderCartDO, form.getContainerPrice(), form.getContainerNum());
            } else {
                this.addOrderCart(form, OrderConstant.UNIT_CONTAINER, customerId);
            }
        }
    }

    @Override
    public Pager<OrderCartResult> queryOrderCartPage(OrderCartQueryOption queryOption, CustomerDO customerDO) {
        Pager<OrderCartResult> pageUtils = new Pager<>();
        Map<String, Object> queryParams = new MapHelper<>().putBeansToMap(queryOption);
        queryParams.put("customerId", customerDO.getId());
        int count = orderCartDao.count(queryParams);
        if (count > 0) {
            List<OrderCartDO> list = orderCartDao.list(queryParams);
            List<Long> goodsIds = list.stream().map(OrderCartDO::getGoodsId).collect(Collectors.toList());
            List<GoodsDO> goodsDOList = goodsDao.getList(goodsIds);
            Map<Long, GoodsPriceModel> goodsListRealPrice = goodsService.getGoodsListRealPrice(goodsIds, customerDO);
            List<OrderCartResult> result = list.stream().map(e -> {
                OrderCartResult orderCartResult = BeanUtils.copyProperties(e, OrderCartResult.class);
                goodsDOList.forEach(e1 -> {
                    if (e1.getId().equals(e.getGoodsId())) {
                        orderCartResult.setName(e1.getName());
                        orderCartResult.setGoodsImg(localHost + e1.getGoodsImg());
                        orderCartResult.setCategoryId(e1.getCategoryId());
                        orderCartResult.setSpecifications(e1.getSpecifications());
                        orderCartResult.setVar01(e1.getVar01());
                    }
                });
                GoodsPriceModel goodsPriceModel = goodsListRealPrice.get(e.getGoodsId());
                if (e.getUnit().equals(OrderConstant.UNIT_BULK)) {
                    if (!goodsPriceModel.getBulkPrice().equals(e.getUnitPrice())) {
                        orderCartResult.setStatus(1);
                    }
                } else {
                    if (!goodsPriceModel.getContainerPrice().equals(e.getUnitPrice())) {
                        orderCartResult.setStatus(1);
                    }
                }
                return orderCartResult;
            }).collect(Collectors.toList());
            pageUtils = new Pager<>(result, count);
        }
        return pageUtils;
    }

    /**
     * 更新
     *
     * @param orderCartDO
     * @param price
     * @param num
     */
    private void updateOrderCart(OrderCartDO orderCartDO, Integer price, Integer num) {
        orderCartDO.setUnitPrice(price);
        orderCartDO.setNum(num);
        this.update(orderCartDO);
    }

    private void addOrderCart(OrderCartForm form, Integer unit, Long customerId) {
        GoodsDO goodsDO = goodsDao.get(form.getGoodsId());
        Assert.notNull(goodsDO, "[添加购物车]失败: 商品不存在");
        Integer businessId = businessCache.toBusiness(goodsDO.getCategoryId());
        Assert.notNull(businessId, "[添加购物车]失败: 商品信息错误,供应商不存在");
        OrderCartDO orderCartDO = new OrderCartDO();
        orderCartDO.setGoodsId(form.getGoodsId());
        orderCartDO.setCustomerId(customerId);
        orderCartDO.setUnit(unit);
        orderCartDO.setVar01(goodsDO.getCategoryId());
        orderCartDO.setVar02(businessId);
        if (unit.equals(OrderConstant.UNIT_BULK)) {
            orderCartDO.setNum(form.getBulkNum());
            orderCartDO.setUnitPrice(form.getBulkPrice());
        } else {
            orderCartDO.setNum(form.getContainerNum());
            orderCartDO.setUnitPrice(form.getContainerPrice());
        }
        this.save(orderCartDO);
    }

    /**
     * 获取当前用户对应的购物车商品、单位信息
     *
     * @param customerId
     * @param goodsId
     * @param unit
     * @return
     */
    private OrderCartDO getCustomerOrderCart(Long customerId, Long goodsId, Integer unit) {
        OrderCartDO orderCartDO = null;
        Map<String, Object> params = Maps.newHashMap();
        params.put("customerId", customerId);
        params.put("goodsId", goodsId);
        params.put("unit", unit);
        List<OrderCartDO> list = orderCartDao.list(params);
        if (CollectionUtils.isNotEmpty(list)) {
            orderCartDO = list.get(0);
        }
        return orderCartDO;
    }

    /**
     * 购物车参数校验
     *
     * @param form
     */
    private void cartParamsVali(OrderCartForm form) {
        if (form.getBulkNum() != null) {
            if (form.getBulkPrice() == null) {
                throw new IllegalArgumentException("[添加购物车]失败: 单价不能为空");
            }
        }
        if (form.getBulkPrice() != null) {
            if (form.getBulkNum() == null) {
                throw new IllegalArgumentException("[添加购物车]失败: 数量不能为空");
            }
        }
        if (form.getContainerNum() != null) {
            if (form.getContainerPrice() == null) {
                throw new IllegalArgumentException("[添加购物车]失败: 单价不能为空");
            }
        }
        if (form.getContainerPrice() != null) {
            if (form.getContainerNum() == null) {
                throw new IllegalArgumentException("[添加购物车]失败: 数量不能为空");
            }
        }
    }

}
