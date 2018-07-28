package com.dm.trade.api;

import com.dm.trade.api.dto.APIResult;
import com.dm.trade.api.dto.request.h5.GoodsListQueryOptionH5;
import com.dm.trade.common.utils.StringUtils;
import com.dm.trade.goods.domain.GoodsCategoryDO;
import com.dm.trade.goods.domain.GoodsDO;
import com.dm.trade.goods.service.GoodsCategoryService;
import com.dm.trade.goods.service.GoodsService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author zhangle1
 * @title APIH5Controller.java
 * @Date 2018-07-15
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "/api/relation")
public class APIH5Controller {

    @Autowired
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/h5list")
    public String h5list(Model model, HttpServletRequest request) throws Exception {
        Map<String, Object> dishTypeParams = Maps.newHashMap();
        dishTypeParams.put("sort", "id");
        dishTypeParams.put("order", "desc");
        List<GoodsCategoryDO> categoryDOS = goodsCategoryService.list(dishTypeParams);
        model.addAttribute("types", categoryDOS);

        return "wx/h5/dish";
    }

    @RequestMapping(value = "/goodsList")
    @ResponseBody
    public APIResult queryDishes(@Valid GoodsListQueryOptionH5 goodsListQueryOption) {
        Map<String, Object> map = Maps.newHashMap();
        if (StringUtils.isNotEmpty(goodsListQueryOption.getName())) {
            map.put("name", goodsListQueryOption.getName());
        }
        if (StringUtils.isNotEmpty(goodsListQueryOption.getCategoryId()+"")) {
            map.put("categoryId", goodsListQueryOption.getCategoryId());
        }
        List<GoodsDO> dicdishesDOS = goodsService.list(map);
        return APIResult.isOk(dicdishesDOS);
    }
}
