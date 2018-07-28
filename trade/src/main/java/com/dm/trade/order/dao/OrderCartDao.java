package com.dm.trade.order.dao;

import com.dm.trade.order.domain.OrderCartDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 购物车
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-15 15:53:41
 */
@Mapper
public interface OrderCartDao {

    OrderCartDO get(Integer id);

    /**
     * 根据购物车id集合批量获取
     *
     * @param ids
     * @return
     */
    List<OrderCartDO> getByIds(List<Long> ids);

    List<OrderCartDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(OrderCartDO orderCart);

    int update(OrderCartDO orderCart);

    int remove(Integer id);

    int batchRemove(Long[] ids);

    /**
     * 获取用户该商品、单位的购物车信息
     *
     * @param orderCartDao
     * @return
     */
    OrderCartDO listByCustomerAndGoodsId(OrderCartDao orderCartDao);
}
