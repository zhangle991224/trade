package com.dm.trade.order.controller;

import com.dm.trade.common.utils.PageUtils;
import com.dm.trade.common.utils.Query;
import com.dm.trade.common.utils.R;
import com.dm.trade.common.utils.ShiroUtils;
import com.dm.trade.order.bean.OrderListBean;
import com.dm.trade.order.domain.OrderDO;
import com.dm.trade.order.service.OrderService;
import com.dm.trade.system.domain.UserDO;
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
 * 订单表
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-15 15:53:40
 */

@Controller
@RequestMapping("/trade/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping()
    @RequiresPermissions("trade:order:order")
    public String Order() {
        return "order/order/order";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("trade:order:order")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        UserDO user = ShiroUtils.getUser();
        params.put("var02", user.getUserId());
        //查询列表数据
        Query query = new Query(params);
        List<OrderListBean> orderList = orderService.listbk(query);
        int total = orderService.count(query);
        PageUtils pageUtils = new PageUtils(orderList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("trade:order:add")
    String add() {
        return "trade/order/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("trade:order:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        OrderDO order = orderService.get(id);
        model.addAttribute("order", order);
        return "trade/order/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("trade:order:add")
    public R save(OrderDO order) {
        if (orderService.save(order) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("trade:order:edit")
    public R update(OrderDO order) {
        orderService.update(order);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("trade:order:remove")
    public R remove(Integer id) {
        if (orderService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("trade:order:batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] ids) {
        orderService.batchRemove(ids);
        return R.ok();
    }

    /**
     * 修改订单状态
     *
     * @param orderId
     * @return
     */
    @PostMapping("/send")
    @ResponseBody
    @RequiresPermissions("trade:order:send")
    public R send(Long orderId, Integer status) {
        OrderDO orderDO = orderService.get(orderId);
        orderDO.setStatus(status);
        orderService.update(orderDO);
        return R.ok();
    }

}
