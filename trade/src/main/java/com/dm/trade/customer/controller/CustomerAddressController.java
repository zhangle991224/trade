package com.dm.trade.customer.controller;

import com.dm.trade.common.utils.PageUtils;
import com.dm.trade.common.utils.Query;
import com.dm.trade.common.utils.R;
import com.dm.trade.customer.domain.CustomerAddressDO;
import com.dm.trade.customer.service.CustomerAddressService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 用户地址表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-03-25 11:48:05
 */
 
@Controller
@RequestMapping("/trade/customerAddress")
public class CustomerAddressController {
	@Autowired
	private CustomerAddressService customerAddressService;
	
	@GetMapping()
	@RequiresPermissions("trade:customerAddress:customerAddress")
	String CustomerAddress(){
	    return "trade/customerAddress/customerAddress";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("trade:customerAddress:customerAddress")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CustomerAddressDO> customerAddressList = customerAddressService.list(query);
		int total = customerAddressService.count(query);
		PageUtils pageUtils = new PageUtils(customerAddressList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("trade:customerAddress:add")
	String add(){
	    return "trade/customerAddress/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("trade:customerAddress:edit")
	String edit(@PathVariable("id") Long id,Model model){
		CustomerAddressDO customerAddress = customerAddressService.get(id);
		model.addAttribute("customerAddress", customerAddress);
	    return "trade/customerAddress/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("trade:customerAddress:add")
	public R save( CustomerAddressDO customerAddress){
		if(customerAddressService.save(customerAddress)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("trade:customerAddress:edit")
	public R update( CustomerAddressDO customerAddress){
		customerAddressService.update(customerAddress);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("trade:customerAddress:remove")
	public R remove( Integer id){
		if(customerAddressService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("trade:customerAddress:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		customerAddressService.batchRemove(ids);
		return R.ok();
	}
	
}
