package com.dm.trade.order.controller;

import com.dm.trade.common.utils.PageUtils;
import com.dm.trade.common.utils.Query;
import com.dm.trade.common.utils.R;
import com.dm.trade.order.domain.OrderDetailDO;
import com.dm.trade.order.service.OrderDetailService;
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
 * 订单详情表
 * 
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-15 15:53:41
 */
 
@Controller
@RequestMapping("/trade/orderDetail")
public class OrderDetailController {
	@Autowired
	private OrderDetailService orderDetailService;
	
	@GetMapping()
	@RequiresPermissions("trade:orderDetail:orderDetail")
	String OrderDetail(){
	    return "trade/orderDetail/orderDetail";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("trade:orderDetail:orderDetail")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<OrderDetailDO> orderDetailList = orderDetailService.list(query);
		int total = orderDetailService.count(query);
		PageUtils pageUtils = new PageUtils(orderDetailList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("trade:orderDetail:add")
	String add(){
	    return "trade/orderDetail/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("trade:orderDetail:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		OrderDetailDO orderDetail = orderDetailService.get(id);
		model.addAttribute("orderDetail", orderDetail);
	    return "trade/orderDetail/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("trade:orderDetail:add")
	public R save( OrderDetailDO orderDetail){
		if(orderDetailService.save(orderDetail)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("trade:orderDetail:edit")
	public R update( OrderDetailDO orderDetail){
		orderDetailService.update(orderDetail);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("trade:orderDetail:remove")
	public R remove( Integer id){
		if(orderDetailService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("trade:orderDetail:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		orderDetailService.batchRemove(ids);
		return R.ok();
	}
	
}
