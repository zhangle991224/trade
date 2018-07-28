package com.dm.trade.goods.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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

import com.dm.trade.goods.domain.GoodsActivityDO;
import com.dm.trade.goods.model.GoodsPriceModel;
import com.dm.trade.goods.service.GoodsActivityService;
import com.dm.trade.goods.service.GoodsService;
import com.dm.trade.api.dto.APIResult;
import com.dm.trade.common.utils.PageUtils;
import com.dm.trade.common.utils.Query;
import com.dm.trade.common.utils.R;
import com.dm.trade.customer.domain.CustomerDO;

/**
 * 商品活动表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-01 21:57:41
 */
 
@Controller
@RequestMapping("/trade/goodsActivity")
public class GoodsActivityController {
	@Resource
	private GoodsService goodsService;
	@Autowired
	private GoodsActivityService goodsActivityService;
	
	@GetMapping()
	@RequiresPermissions("trade:goodsActivity:goodsActivity")
	String GoodsActivity(){
	    return "trade/goodsActivity/goodsActivity";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("trade:goodsActivity:goodsActivity")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<GoodsActivityDO> goodsActivityList = goodsActivityService.list(query);
		int total = goodsActivityService.count(query);
		PageUtils pageUtils = new PageUtils(goodsActivityList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("trade:goodsActivity:add")
	String add(){
	    return "trade/goodsActivity/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("trade:goodsActivity:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		GoodsActivityDO goodsActivity = goodsActivityService.get(id);
		model.addAttribute("goodsActivity", goodsActivity);
	    return "trade/goodsActivity/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("trade:goodsActivity:add")
	public R save( GoodsActivityDO goodsActivity){
		if(goodsActivityService.save(goodsActivity)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("trade:goodsActivity:edit")
	public R update( GoodsActivityDO goodsActivity){
		goodsActivityService.update(goodsActivity);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("trade:goodsActivity:remove")
	public R remove( Integer id){
		if(goodsActivityService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("trade:goodsActivity:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		goodsActivityService.batchRemove(ids);
		return R.ok();
	}
	
}
