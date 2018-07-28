package com.dm.trade.goods.dao;

import com.dm.trade.goods.domain.GoodsCategoryDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品类别表
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-01 21:57:41
 */
@Mapper
public interface GoodsCategoryDao {

	GoodsCategoryDO get(Integer id);
	
	List<GoodsCategoryDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(GoodsCategoryDO goodsCategory);
	
	int update(GoodsCategoryDO goodsCategory);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	List<GoodsCategoryDO> getByIds(@Param("ids") List<Integer> ids);
}
