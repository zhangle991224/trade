package com.dm.trade.goods.controller;

import com.dm.trade.common.cache.CategoryBusinessCache;
import com.dm.trade.common.utils.PageUtils;
import com.dm.trade.common.utils.Query;
import com.dm.trade.common.utils.R;
import com.dm.trade.common.utils.ShiroUtils;
import com.dm.trade.goods.domain.GoodsCategoryDO;
import com.dm.trade.goods.domain.GoodsDO;
import com.dm.trade.goods.service.GoodsCategoryService;
import com.dm.trade.goods.service.GoodsService;
import com.dm.trade.system.domain.UserDO;
import com.google.common.collect.Maps;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商品类别表
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-01 21:57:40
 */

@Controller
@RequestMapping("/trade/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsCategoryService categoryService;

    @Autowired
    private CategoryBusinessCache categoryBusinessCache;

    @GetMapping()
    @RequiresPermissions("trade:goods:goods")
    String Goods() {
        return "goods/goods/goods";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("trade:goods:goods")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        UserDO user = ShiroUtils.getUser();
        List<GoodsDO> goodsList = null;
        List<GoodsCategoryDO> categories;
        //查询列表数据
        Query query = new Query(params);
        if (user.getUsername().equals("admin")) {
            Set<Integer> categoryIds = goodsList.stream().map(GoodsDO::getCategoryId).collect(Collectors.toSet());
            categories = categoryService.getCategoriesByIds(new ArrayList<>(categoryIds));
        } else {
            categories = categoryBusinessCache.getCategoriesDoByBusinessId(user.getUserId().intValue());
            List<Integer> categoryIds = categories.stream().map(GoodsCategoryDO::getId).collect(Collectors.toList());
            query.put("categoryIds", categoryIds);
            goodsList = goodsService.list(query);
        }

        goodsList.forEach(e -> {
            Optional<GoodsCategoryDO> any = categories.stream().filter(c -> c.getId().equals(e.getCategoryId())).findAny();
            any.ifPresent(goodsCategoryDO -> e.setCategoryName(goodsCategoryDO.getName()));
        });
        int total = goodsService.count(query);
        return new PageUtils(goodsList, total);
    }

    @GetMapping("/add")
    @RequiresPermissions("trade:goods:add")
    public String add(Model model) {
        // 获取所有类别
        List<GoodsCategoryDO> categoryDOS = categoryService.list(Maps.newHashMap());
        model.addAttribute("categorys", categoryDOS);
        return "goods/goods/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("trade:goods:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        GoodsDO goods = goodsService.get(id);
        Integer var01 = goods.getVar01();
        Integer containerPrice = goods.getContainerPrice();
        if (var01 != null) {
            goods.setVar01(var01 / 100);
        }
        if (containerPrice != null) {
            goods.setContainerPrice(containerPrice / 100);
        }
        // 获取所有类别
        List<GoodsCategoryDO> categoryDOS = categoryService.list(Maps.newHashMap());
        model.addAttribute("categorys", categoryDOS);
        model.addAttribute("goods", goods);
        return "goods/goods/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("trade:goods:add")
    public R save(GoodsDO goods) {
        Integer var01 = goods.getVar01();
        Integer containerPrice = goods.getContainerPrice();
        if (var01 != null) {
            goods.setVar01(var01 * 100);
        }
        if (containerPrice != null) {
            goods.setContainerPrice(containerPrice * 100);
        }
        if (goodsService.save(goods) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("trade:goods:edit")
    public R update(GoodsDO goods) {
        Integer var01 = goods.getVar01();
        Integer containerPrice = goods.getContainerPrice();
        if (var01 != null) {
            goods.setVar01(var01 * 100);
        }
        if (containerPrice != null) {
            goods.setContainerPrice(containerPrice * 100);
        }
        goodsService.update(goods);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("trade:goods:remove")
    public R remove(Integer id) {
        if (goodsService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("trade:goods:batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] ids) {
        goodsService.batchRemove(ids);
        return R.ok();
    }

}
