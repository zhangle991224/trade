package com.dm.trade.order.dao;

import com.dm.trade.order.domain.OrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 订单表
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-15 15:53:40
 */
@Mapper
public interface OrderDao {

	OrderDO get(Long id);
	
	List<OrderDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OrderDO order);
	
	int update(OrderDO order);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
