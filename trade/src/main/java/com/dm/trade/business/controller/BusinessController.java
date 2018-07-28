package com.dm.trade.business.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dm.trade.business.domain.BusinessDO;
import com.dm.trade.business.service.BusinessService;
import com.dm.trade.common.utils.PageUtils;
import com.dm.trade.common.utils.Query;
import com.dm.trade.common.utils.R;

/**
 * 商家信息表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-06-08 21:41:41
 */
 
@Controller
@RequestMapping("/trade/business")
public class BusinessController {
	@Autowired
	private BusinessService businessService;
	
	@GetMapping()
	@RequiresPermissions("trade:business:business")
	String Business(){
	    return "business/business";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("trade:business:business")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<BusinessDO> businessList = businessService.list(query);
		int total = businessService.count(query);
		PageUtils pageUtils = new PageUtils(businessList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("trade:business:add")
	String add(){
	    return "trade/business/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("trade:business:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		BusinessDO business = businessService.get(id);
		model.addAttribute("business", business);
	    return "trade/business/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("trade:business:add")
	public R save( BusinessDO business){
		if(businessService.save(business)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("trade:business:edit")
	public R update( BusinessDO business){
		businessService.update(business);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("trade:business:remove")
	public R remove( Integer id){
		if(businessService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("trade:business:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		businessService.batchRemove(ids);
		return R.ok();
	}
	
}
