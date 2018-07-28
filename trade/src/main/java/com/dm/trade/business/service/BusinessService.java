package com.dm.trade.business.service;

import com.dm.trade.business.domain.BusinessDO;

import java.util.List;
import java.util.Map;

/**
 * 商家信息表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-06-08 21:41:41
 */
public interface BusinessService {
	
	BusinessDO get(Integer id);
	
	List<BusinessDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BusinessDO business);
	
	int update(BusinessDO business);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
