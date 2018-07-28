package com.dm.trade.goods.controller;

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

import com.dm.trade.goods.domain.GoodsCategoryDO;
import com.dm.trade.goods.service.GoodsCategoryService;
import com.dm.trade.common.utils.PageUtils;
import com.dm.trade.common.utils.Query;
import com.dm.trade.common.utils.R;

/**
 * 商品类别表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-01 21:57:41
 */
 
@Controller
@RequestMapping("/trade/goodsCategory")
public class GoodsCategoryController {
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	
	@GetMapping()
	@RequiresPermissions("trade:goodsCategory:goodsCategory")
	String GoodsCategory(){
	    return "trade/goodsCategory/goodsCategory";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("trade:goodsCategory:goodsCategory")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<GoodsCategoryDO> goodsCategoryList = goodsCategoryService.list(query);
		int total = goodsCategoryService.count(query);
		return new PageUtils(goodsCategoryList, total);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("trade:goodsCategory:add")
	String add(){
	    return "trade/goodsCategory/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("trade:goodsCategory:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		GoodsCategoryDO goodsCategory = goodsCategoryService.get(id);
		model.addAttribute("goodsCategory", goodsCategory);
	    return "trade/goodsCategory/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("trade:goodsCategory:add")
	public R save( GoodsCategoryDO goodsCategory){
		if(goodsCategoryService.save(goodsCategory)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("trade:goodsCategory:edit")
	public R update( GoodsCategoryDO goodsCategory){
		goodsCategoryService.update(goodsCategory);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("trade:goodsCategory:remove")
	public R remove( Integer id){
		if(goodsCategoryService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("trade:goodsCategory:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		goodsCategoryService.batchRemove(ids);
		return R.ok();
	}
	
}
