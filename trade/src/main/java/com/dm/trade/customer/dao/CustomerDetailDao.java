package com.dm.trade.customer.dao;

import com.dm.trade.customer.domain.CustomerDetailDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户详情表
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-03-25 11:48:05
 */
@Mapper
public interface CustomerDetailDao {

	CustomerDetailDO get(Integer id);
	
	List<CustomerDetailDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(CustomerDetailDO customerDetail);
	
	int update(CustomerDetailDO customerDetail);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
