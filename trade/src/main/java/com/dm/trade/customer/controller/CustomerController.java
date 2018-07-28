package com.dm.trade.customer.controller;

import com.dm.trade.common.utils.PageUtils;
import com.dm.trade.common.utils.Query;
import com.dm.trade.common.utils.R;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.customer.service.CustomerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 用户信息 主表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-03-25 11:29:15
 */
 
@Controller
@RequestMapping("/trade/customer")
public class CustomerController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CustomerService customerService;

	@GetMapping()
	@RequiresPermissions("trade:customer:customer")
	String Customer(){
	    return "customer/customer";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("trade:customer:customer")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CustomerDO> customerList = customerService.list(query);
		int total = customerService.count(query);
		PageUtils pageUtils = new PageUtils(customerList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("trade:customer:add")
	String add(){
	    return "trade/customer/add";
	}

	@GetMapping("/edit/{id}")
//	@RequiresPermissions("trade:customer:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		CustomerDO customer = customerService.get(id);
		model.addAttribute("customer", customer);
	    return "customer/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("trade:customer:add")
	public R save( CustomerDO customer){
		if(customerService.save(customer)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("trade:customer:edit")
	public R update( CustomerDO customer){
		customerService.update(customer);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
//	@RequiresPermissions("trade:customer:remove")
	public R remove( Integer id){
		if(customerService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("trade:customer:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		customerService.batchRemove(ids);
		return R.ok();
	}

}
