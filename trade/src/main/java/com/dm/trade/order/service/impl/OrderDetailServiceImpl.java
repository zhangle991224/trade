package com.dm.trade.order.service.impl;

import com.dm.trade.api.dto.response.order.OrderDetailResult;
import com.dm.trade.common.utils.BeanUtils;
import com.dm.trade.goods.dao.GoodsDao;
import com.dm.trade.goods.domain.GoodsDO;
import com.dm.trade.order.dao.OrderDetailDao;
import com.dm.trade.order.domain.OrderDetailDO;
import com.dm.trade.order.service.OrderDetailService;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private GoodsDao goodsDao;

    @Value("${app.local.host}")
    private String localHost;

    @Override
    public OrderDetailDO get(Integer id) {
        return orderDetailDao.get(id);
    }

    @Override
    public List<OrderDetailDO> list(Map<String, Object> map) {
        return orderDetailDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return orderDetailDao.count(map);
    }

    @Override
    public int save(OrderDetailDO orderDetail) {
        return orderDetailDao.save(orderDetail);
    }

    @Override
    public int update(OrderDetailDO orderDetail) {
        return orderDetailDao.update(orderDetail);
    }

    @Override
    public int remove(Integer id) {
        return orderDetailDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return orderDetailDao.batchRemove(ids);
    }

    @Override
    public List<OrderDetailResult> getOrderDetails(Long orderId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("orderId", orderId);
        List<OrderDetailDO> list = orderDetailDao.list(params);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<Long> goodsIdList = list.stream().map(OrderDetailDO::getGoodsId).collect(Collectors.toList());
        List<GoodsDO> goodsDOS = goodsDao.getList(goodsIdList);
        Map<Long, GoodsDO> goodsDOMap = goodsDOS.stream().collect(Collectors.toMap(GoodsDO::getId, Function.identity()));
        return list.stream().map(e -> {
            OrderDetailResult orderDetailResult = BeanUtils.copyProperties(e, OrderDetailResult.class);
            GoodsDO goodsDO = goodsDOMap.get(e.getGoodsId());
            orderDetailResult.setGoodsImg(localHost + goodsDO.getGoodsImg());
            orderDetailResult.setName(goodsDO.getName());
            orderDetailResult.setCategoryId(goodsDO.getCategoryId());
            return orderDetailResult;
        }).collect(Collectors.toList());
    }

}
