package com.dm.trade.customer.controller;

import com.dm.trade.common.utils.PageUtils;
import com.dm.trade.common.utils.Query;
import com.dm.trade.common.utils.R;
import com.dm.trade.customer.domain.CustomerDetailDO;
import com.dm.trade.customer.service.CustomerDetailService;
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
 * 用户详情表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-03-25 11:48:05
 */
 
@Controller
@RequestMapping("/trade/customerDetail")
public class CustomerDetailController {
	@Autowired
	private CustomerDetailService customerDetailService;
	
	@GetMapping()
	@RequiresPermissions("trade:customerDetail:customerDetail")
	String CustomerDetail(){
	    return "trade/customerDetail/customerDetail";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("trade:customerDetail:customerDetail")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CustomerDetailDO> customerDetailList = customerDetailService.list(query);
		int total = customerDetailService.count(query);
		PageUtils pageUtils = new PageUtils(customerDetailList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("trade:customerDetail:add")
	String add(){
	    return "trade/customerDetail/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("trade:customerDetail:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		CustomerDetailDO customerDetail = customerDetailService.get(id);
		model.addAttribute("customerDetail", customerDetail);
	    return "trade/customerDetail/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("trade:customerDetail:add")
	public R save( CustomerDetailDO customerDetail){
		if(customerDetailService.save(customerDetail)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("trade:customerDetail:edit")
	public R update( CustomerDetailDO customerDetail){
		customerDetailService.update(customerDetail);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("trade:customerDetail:remove")
	public R remove( Integer id){
		if(customerDetailService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("trade:customerDetail:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		customerDetailService.batchRemove(ids);
		return R.ok();
	}
	
}
