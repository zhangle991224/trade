package com.dm.trade.goods.service;

import com.dm.trade.api.dto.Pager;
import com.dm.trade.api.dto.request.goods.GoodsListQueryOption;
import com.dm.trade.api.dto.response.goods.GoodsListResult;
import com.dm.trade.common.utils.PageUtils;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.goods.domain.GoodsDO;
import com.dm.trade.goods.model.GoodsPriceModel;

import java.util.List;
import java.util.Map;

/**
 * 商品类别表
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-01 21:57:40
 */
public interface GoodsService {

    GoodsDO get(Long id);

    List<GoodsDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(GoodsDO goods);

    int update(GoodsDO goods);

    int remove(Integer id);

    int batchRemove(Integer[] ids);

    /**
     * 根据条件获取商品列表
     *
     * @param queryOption
     * @return
     */
    Pager<GoodsListResult> getAPIGoodsList(GoodsListQueryOption queryOption, CustomerDO customerDO);


    /**
     * 获取商品价格
     * <p>1. 获取当前商品是否存在活动期</p>
     * <p>2. 判断该用户是否用友活动权限</p>
     * <p>3. 是否根据用户等级进行商品价格浮动(后期 todo)</p>
     *
     * @param goodsId    商品
     * @param customerDO 用户信息
     * @return
     */
    GoodsPriceModel getGoodsRealPrice(Long goodsId, CustomerDO customerDO);


    Map<Long, GoodsPriceModel> getGoodsListRealPrice(List<Long> goodsIds, CustomerDO customerDO);
}
