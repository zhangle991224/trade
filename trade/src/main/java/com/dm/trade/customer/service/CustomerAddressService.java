package com.dm.trade.customer.service;

import com.dm.trade.api.dto.request.customerAddress.CustomerAddressForm;
import com.dm.trade.customer.domain.CustomerAddressDO;

import java.util.List;
import java.util.Map;

/**
 * 用户地址表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-03-25 11:48:05
 */
public interface CustomerAddressService {
	
	CustomerAddressDO get(Long id);
	
	List<CustomerAddressDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(CustomerAddressDO customerAddress);
	
	int update(CustomerAddressDO customerAddress);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	void addAddress(CustomerAddressForm customerAddressForm,long customerId);

	void updateAddress(CustomerAddressForm customerAddressForm,long customerId);
}
