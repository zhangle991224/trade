package com.dm.trade.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.dm.trade.api.dto.Pager;
import com.dm.trade.api.dto.request.goods.GoodsListQueryOption;
import com.dm.trade.api.dto.response.goods.GoodsListResult;
import com.dm.trade.common.config.constant.OrderConstant;
import com.dm.trade.common.utils.BeanUtils;
import com.dm.trade.common.utils.MapHelper;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.goods.dao.GoodsActivityDao;
import com.dm.trade.goods.dao.GoodsDao;
import com.dm.trade.goods.domain.GoodsActivityDO;
import com.dm.trade.goods.domain.GoodsDO;
import com.dm.trade.goods.model.GoodsPriceModel;
import com.dm.trade.goods.service.GoodsService;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsActivityDao goodsActivityDao;

    @Value("${app.local.host}")
    private String localHost;

    @Override
    public GoodsDO get(Long id) {
        return goodsDao.get(id);
    }

    @Override
    public List<GoodsDO> list(Map<String, Object> map) {
        return goodsDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return goodsDao.count(map);
    }

    @Override
    public int save(GoodsDO goods) {
        return goodsDao.save(goods);
    }

    @Override
    public int update(GoodsDO goods) {
        return goodsDao.update(goods);
    }

    @Override
    public int remove(Integer id) {
        return goodsDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return goodsDao.batchRemove(ids);
    }

    @Override
    public Pager<GoodsListResult> getAPIGoodsList(GoodsListQueryOption queryOption, CustomerDO customerDO) {
        Map<String, Object> params = new MapHelper<>().putBeansToMap(queryOption);
        int total = goodsDao.count(params);
        if (total == 0) {
            return new Pager<>();
        }
        List<GoodsDO> list = goodsDao.list(params);
        Map<Long, GoodsPriceModel> goodsPriceModelMap = calGoodsListRealPrice(list, customerDO);
        List<GoodsListResult> collect = list.stream()
                .map(e -> {
                    GoodsListResult goodsListResult = BeanUtils.copyProperties(e, GoodsListResult.class);
                    GoodsPriceModel goodsPriceModel = goodsPriceModelMap.get(e.getId());
                    goodsListResult.setBulkPrice(goodsPriceModel.getBulkPrice());
                    goodsListResult.setContainerPrice(goodsPriceModel.getContainerPrice());
                    goodsListResult.setGoodsImg(localHost + goodsListResult.getGoodsImg());
                    return goodsListResult;
                })
                .collect(Collectors.toList());
        return new Pager<>(collect, total);
    }

    @Override
    public GoodsPriceModel getGoodsRealPrice(Long goodsId, CustomerDO customerDO) {
        GoodsPriceModel goodsPriceModel = new GoodsPriceModel(goodsId);
        // 获取商品信息
        GoodsDO goodsDO = this.get(goodsId);
        if (goodsDO == null) {
            throw new IllegalArgumentException("[参数错误] 商品不存在 goodsId:" + goodsId);
        }
        goodsPriceModel.setVar01(goodsDO.getVar01());
        goodsPriceModel.setContainerPrice(goodsDO.getContainerPrice());
        goodsPriceModel.setBulkPrice(goodsDO.getBulkPrice());
        // 获取活动信息
        List<GoodsActivityDO> goodsActivity = this.getGoodsActivity(goodsId);
        if (CollectionUtils.isNotEmpty(goodsActivity)) {
            goodsActivity.forEach(e -> {
                if (OrderConstant.UNIT_BULK.equals(e.getUnit())) {
                    goodsPriceModel.setBulkPrice(e.getActivityPrice());
                    goodsPriceModel.setActivity(true);
                }
                if (OrderConstant.UNIT_CONTAINER.equals(e.getUnit())) {
                    goodsPriceModel.setContainerPrice(e.getActivityPrice());
                    goodsPriceModel.setActivity(true);
                }
            });
        }

        return goodsPriceModel;
    }

    @Override
    public Map<Long, GoodsPriceModel> getGoodsListRealPrice(List<Long> goodsIds, CustomerDO customerDO) {
        List<GoodsDO> goodsDOS = goodsDao.getList(goodsIds);
        if (CollectionUtils.isEmpty(goodsDOS)) {
            throw new IllegalArgumentException("[参数错误] 商品不存在 goodsIds:" + JSON.toJSONString(goodsIds));
        }
        return calGoodsListRealPrice(goodsDOS, customerDO);
    }

    /**
     * 批量获取价格信息
     * <p>商品价格列表获取价格时调用该方法</p>
     *
     * @param goodsDOs   已获取到的商品信息
     * @param goodsIds   商品id列表，防止多次循环造成的性能消耗
     * @param customerDO 用户信息
     * @return
     */
    private Map<Long, GoodsPriceModel> calGoodsListRealPrice(List<GoodsDO> goodsDOs, CustomerDO customerDO) {
        Map<Long, GoodsPriceModel> mapResult = Maps.newHashMap();
        List<Long> goodsIdList = Lists.newArrayList();
        for (GoodsDO goodsDO : goodsDOs) {
            mapResult.put(goodsDO.getId(), GoodsPriceModel.goodsPriceModel(goodsDO));
            goodsIdList.add(goodsDO.getId());
        }
        List<GoodsActivityDO> goodsListActivity = this.getGoodsListActivity(goodsIdList);
        if (CollectionUtils.isEmpty(goodsListActivity)) {
            return mapResult;
        } else {
            goodsListActivity.forEach(e -> {
                GoodsPriceModel goodsPriceModel = mapResult.get(e.getGoodsId());
                if (goodsPriceModel != null) {
                    if (OrderConstant.UNIT_BULK.equals(e.getUnit())) {
                        goodsPriceModel.setBulkPrice(e.getActivityPrice());
                        goodsPriceModel.setActivity(true);
                    }
                    if (OrderConstant.UNIT_CONTAINER.equals(e.getUnit())) {
                        goodsPriceModel.setContainerPrice(e.getActivityPrice());
                        goodsPriceModel.setActivity(true);
                    }
                }
            });
        }
        return mapResult;
    }

    private List<GoodsActivityDO> getGoodsListActivity(List<Long> goodsIds) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("goodsIds", goodsIds);
        params.put("status", 1);
        return goodsActivityDao.list(params);
    }

    private List<GoodsActivityDO> getGoodsActivity(Long goodsId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("goodsId", goodsId);
        params.put("status", 1);
        return goodsActivityDao.list(params);
    }

}
