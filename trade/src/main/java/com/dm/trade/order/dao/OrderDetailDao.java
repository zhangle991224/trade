package com.dm.trade.order.dao;

import com.dm.trade.order.domain.OrderDetailDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 订单详情表
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-15 15:53:41
 */
@Mapper
public interface OrderDetailDao {

    OrderDetailDO get(Integer id);

    List<OrderDetailDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(OrderDetailDO orderDetail);

    int batchSave(List<OrderDetailDO> orderDetails);

    int update(OrderDetailDO orderDetail);

    int remove(Integer id);

    int batchRemove(Integer[] ids);
}
