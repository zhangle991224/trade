package com.dm.trade.customer.service.impl;

import com.dm.trade.api.dto.request.customerAddress.CustomerAddressForm;
import com.dm.trade.customer.dao.CustomerAddressDao;
import com.dm.trade.customer.domain.CustomerAddressDO;
import com.dm.trade.customer.service.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {
	@Autowired
	private CustomerAddressDao customerAddressDao;
	
	@Override
	public CustomerAddressDO get(Long id){
		return customerAddressDao.get(id);
	}
	
	@Override
	public List<CustomerAddressDO> list(Map<String, Object> map){
		return customerAddressDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return customerAddressDao.count(map);
	}
	
	@Override
	public int save(CustomerAddressDO customerAddress){
		return customerAddressDao.save(customerAddress);
	}
	
	@Override
	public int update(CustomerAddressDO customerAddress){
		return customerAddressDao.update(customerAddress);
	}
	
	@Override
	public int remove(Integer id){
		return customerAddressDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return customerAddressDao.batchRemove(ids);
	}

	/**
	 * 新增地址
	 * @param customerAddressForm
	 * @param customerId
	 */
	@Override
	public void addAddress(CustomerAddressForm customerAddressForm, long customerId) {
		CustomerAddressDO customerAddressDO = new CustomerAddressDO();
		customerAddressDO.setAddress(customerAddressForm.getAddress());
		customerAddressDO.setLinkMan(customerAddressForm.getLinkMan());
		customerAddressDO.setLinkPhone(customerAddressForm.getLinkPhone());
		customerAddressDO.setAreaId(customerAddressForm.getAreaId());
		customerAddressDO.setCityId(customerAddressForm.getCityId());
		customerAddressDO.setProvinceId(customerAddressForm.getProvinceId());
		customerAddressDO.setCustomerId(customerId);
		this.save(customerAddressDO);
	}

	/**
	 * 修改地址
	 * @param customerAddressForm
	 * @param customerId
	 */
	@Override
	public void updateAddress(CustomerAddressForm customerAddressForm, long customerId) {
		CustomerAddressDO customerAddressDO = new CustomerAddressDO();
		customerAddressDO.setId(customerAddressForm.getId());
		customerAddressDO.setAddress(customerAddressForm.getAddress());
		customerAddressDO.setLinkMan(customerAddressForm.getLinkMan());
		customerAddressDO.setLinkPhone(customerAddressForm.getLinkPhone());
		customerAddressDO.setAreaId(customerAddressForm.getAreaId());
		customerAddressDO.setCityId(customerAddressForm.getCityId());
		customerAddressDO.setProvinceId(customerAddressForm.getProvinceId());
		customerAddressDO.setCustomerId(customerId);
		this.update(customerAddressDO);
	}
}
