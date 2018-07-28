package com.dm.trade.business.dao;

import com.dm.trade.business.domain.BusinessDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 商家信息表
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-06-08 21:41:41
 */
@Mapper
public interface BusinessDao {

	BusinessDO get(Integer id);
	
	List<BusinessDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BusinessDO business);
	
	int update(BusinessDO business);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
