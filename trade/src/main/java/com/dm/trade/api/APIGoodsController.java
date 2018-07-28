package com.dm.trade.api;

import com.dm.trade.api.dto.APIResult;
import com.dm.trade.api.dto.Pager;
import com.dm.trade.api.dto.request.goods.GoodsListQueryOption;
import com.dm.trade.api.dto.response.goods.GoodsListResult;
import com.dm.trade.common.utils.HttpContextUtils;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.goods.service.GoodsCategoryService;
import com.dm.trade.goods.service.GoodsService;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商品控制器
 * 项目名称：dream-trade
 * 类名称：APIGoodsController
 * 类描述：
 * 创建人：wujun
 * 创建时间：2018年4月1日 下午10:33:57
 */
@RestController
@RequestMapping("/api/goods")
public class APIGoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsCategoryService categoryService;

    /**
     * 根据条件获取商品列表
     *
     * @param queryOption
     * @return
     */
//    @NeedLogin
    @RequestMapping("list")
    public APIResult getGoodsList(GoodsListQueryOption queryOption) {
        CustomerDO customerInfo = HttpContextUtils.getCustomerInfo();
        Pager<GoodsListResult> result = goodsService.getAPIGoodsList(queryOption, customerInfo);
        return APIResult.isOk(result);
    }

    /**
     * 获取所有商品类目
     *
     * @return
     */
    @RequestMapping("categoryList")
    public APIResult getCategoryLists(Integer categoryId) {
        if (categoryId == null) {
            categoryId = 0;
        }
        Map<String, Object> params = Maps.newHashMap();
        params.put("pid", categoryId);
        return APIResult.isOk(categoryService.list(params));
    }


}
