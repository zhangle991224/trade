package com.dm.trade.customer.service.impl;

import com.dm.trade.customer.dao.CustomerDetailDao;
import com.dm.trade.customer.domain.CustomerDetailDO;
import com.dm.trade.customer.service.CustomerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class CustomerDetailServiceImpl implements CustomerDetailService {
	@Autowired
	private CustomerDetailDao customerDetailDao;
	
	@Override
	public CustomerDetailDO get(Integer id){
		return customerDetailDao.get(id);
	}
	
	@Override
	public List<CustomerDetailDO> list(Map<String, Object> map){
		return customerDetailDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return customerDetailDao.count(map);
	}
	
	@Override
	public int save(CustomerDetailDO customerDetail){
		return customerDetailDao.save(customerDetail);
	}
	
	@Override
	public int update(CustomerDetailDO customerDetail){
		return customerDetailDao.update(customerDetail);
	}
	
	@Override
	public int remove(Integer id){
		return customerDetailDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return customerDetailDao.batchRemove(ids);
	}
	
}
