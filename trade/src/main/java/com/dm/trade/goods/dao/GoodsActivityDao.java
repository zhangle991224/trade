package com.dm.trade.goods.dao;

import com.dm.trade.goods.domain.GoodsActivityDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 商品活动表
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-01 21:57:41
 */
@Mapper
public interface GoodsActivityDao {

	GoodsActivityDO get(Integer id);
	
	List<GoodsActivityDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(GoodsActivityDO goodsActivity);
	
	int update(GoodsActivityDO goodsActivity);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
