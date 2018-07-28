package com.dm.trade.order.service;

import com.dm.trade.api.dto.response.order.OrderDetailResult;
import com.dm.trade.order.domain.OrderDetailDO;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.Map;

/**
 * 订单详情表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-15 15:53:41
 */
public interface OrderDetailService {
	
	OrderDetailDO get(Integer id);
	
	List<OrderDetailDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OrderDetailDO orderDetail);
	
	int update(OrderDetailDO orderDetail);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	/**
	 * 获取订单详情
	 * @return
	 */
	List<OrderDetailResult> getOrderDetails(Long orderId);
}
