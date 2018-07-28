package com.dm.trade.customer.dao;

import com.dm.trade.customer.domain.CustomerDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户信息 主表
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-03-25 11:29:15
 */
@Mapper
public interface CustomerDao {

	CustomerDO get(Integer id);
	
	List<CustomerDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	Long save(CustomerDO customer);
	
	int update(CustomerDO customer);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
