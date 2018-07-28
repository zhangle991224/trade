package com.dm.trade.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.dm.trade.goods.dao.GoodsActivityDao;
import com.dm.trade.goods.domain.GoodsActivityDO;
import com.dm.trade.goods.service.GoodsActivityService;



@Service
public class GoodsActivityServiceImpl implements GoodsActivityService {
	@Autowired
	private GoodsActivityDao goodsActivityDao;
	
	@Override
	public GoodsActivityDO get(Integer id){
		return goodsActivityDao.get(id);
	}
	
	@Override
	public List<GoodsActivityDO> list(Map<String, Object> map){
		return goodsActivityDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return goodsActivityDao.count(map);
	}
	
	@Override
	public int save(GoodsActivityDO goodsActivity){
		return goodsActivityDao.save(goodsActivity);
	}
	
	@Override
	public int update(GoodsActivityDO goodsActivity){
		return goodsActivityDao.update(goodsActivity);
	}
	
	@Override
	public int remove(Integer id){
		return goodsActivityDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return goodsActivityDao.batchRemove(ids);
	}
	
}
