package com.dm.trade.customer.service.impl;

import com.dm.trade.customer.dao.CustomerIntegralDao;
import com.dm.trade.customer.domain.CustomerIntegralDO;
import com.dm.trade.customer.service.CustomerIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class CustomerIntegralServiceImpl implements CustomerIntegralService {
	@Autowired
	private CustomerIntegralDao customerIntegralDao;
	
	@Override
	public CustomerIntegralDO get(Integer id){
		return customerIntegralDao.get(id);
	}
	
	@Override
	public List<CustomerIntegralDO> list(Map<String, Object> map){
		return customerIntegralDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return customerIntegralDao.count(map);
	}
	
	@Override
	public int save(CustomerIntegralDO customerIntegral){
		return customerIntegralDao.save(customerIntegral);
	}
	
	@Override
	public int update(CustomerIntegralDO customerIntegral){
		return customerIntegralDao.update(customerIntegral);
	}
	
	@Override
	public int remove(Integer id){
		return customerIntegralDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return customerIntegralDao.batchRemove(ids);
	}
	
}
