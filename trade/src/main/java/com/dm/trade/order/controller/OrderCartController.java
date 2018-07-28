package com.dm.trade.order.controller;

import com.dm.trade.common.utils.PageUtils;
import com.dm.trade.common.utils.Query;
import com.dm.trade.common.utils.R;
import com.dm.trade.order.domain.OrderCartDO;
import com.dm.trade.order.service.OrderCartService;
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
 * 购物车
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-15 15:53:41
 */

@Controller
@RequestMapping("/trade/orderCart")
public class OrderCartController {
    @Autowired
    private OrderCartService orderCartService;

    @GetMapping()
    @RequiresPermissions("trade:orderCart:orderCart")
    String OrderCart() {
        return "trade/orderCart/orderCart";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("trade:orderCart:orderCart")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<OrderCartDO> orderCartList = orderCartService.list(query);
        int total = orderCartService.count(query);
        PageUtils pageUtils = new PageUtils(orderCartList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("trade:orderCart:add")
    String add() {
        return "trade/orderCart/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("trade:orderCart:edit")
    String edit(@PathVariable("id") Integer id, Model model) {
        OrderCartDO orderCart = orderCartService.get(id);
        model.addAttribute("orderCart", orderCart);
        return "trade/orderCart/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("trade:orderCart:add")
    public R save(OrderCartDO orderCart) {
        if (orderCartService.save(orderCart) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("trade:orderCart:edit")
    public R update(OrderCartDO orderCart) {
        orderCartService.update(orderCart);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("trade:orderCart:remove")
    public R remove(Integer id) {
        if (orderCartService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("trade:orderCart:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        orderCartService.batchRemove(ids);
        return R.ok();
    }

}
