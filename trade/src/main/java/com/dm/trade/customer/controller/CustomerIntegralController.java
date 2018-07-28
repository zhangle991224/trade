package com.dm.trade.customer.controller;

import com.dm.trade.common.utils.PageUtils;
import com.dm.trade.common.utils.Query;
import com.dm.trade.common.utils.R;
import com.dm.trade.customer.domain.CustomerIntegralDO;
import com.dm.trade.customer.service.CustomerIntegralService;
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
 * 用户积分记录表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-03-25 11:48:05
 */
 
@Controller
@RequestMapping("/trade/customerIntegral")
public class CustomerIntegralController {
	@Autowired
	private CustomerIntegralService customerIntegralService;
	
	@GetMapping()
	@RequiresPermissions("trade:customerIntegral:customerIntegral")
	String CustomerIntegral(){
	    return "trade/customerIntegral/customerIntegral";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("trade:customerIntegral:customerIntegral")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CustomerIntegralDO> customerIntegralList = customerIntegralService.list(query);
		int total = customerIntegralService.count(query);
		PageUtils pageUtils = new PageUtils(customerIntegralList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("trade:customerIntegral:add")
	String add(){
	    return "trade/customerIntegral/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("trade:customerIntegral:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		CustomerIntegralDO customerIntegral = customerIntegralService.get(id);
		model.addAttribute("customerIntegral", customerIntegral);
	    return "trade/customerIntegral/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("trade:customerIntegral:add")
	public R save( CustomerIntegralDO customerIntegral){
		if(customerIntegralService.save(customerIntegral)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("trade:customerIntegral:edit")
	public R update( CustomerIntegralDO customerIntegral){
		customerIntegralService.update(customerIntegral);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("trade:customerIntegral:remove")
	public R remove( Integer id){
		if(customerIntegralService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("trade:customerIntegral:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		customerIntegralService.batchRemove(ids);
		return R.ok();
	}
	
}
