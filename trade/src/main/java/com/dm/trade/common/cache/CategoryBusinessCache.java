package com.dm.trade.common.cache;

import com.dm.trade.goods.domain.GoodsCategoryDO;
import com.dm.trade.goods.service.GoodsCategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhongchao
 * @title CategoryBusinessCache.java
 * @Date 2018-06-10
 * @since v1.0.0
 */
@Component
public class CategoryBusinessCache extends AbstractCache<Map<Integer, GoodsCategoryDO>> {

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    public CategoryBusinessCache() {
        super(defaultCacheTime);
    }

    @PostConstruct
    public void init() {
        super.init();
    }

    @Override
    Map<Integer, GoodsCategoryDO> getCacheData() {
        List<GoodsCategoryDO> list = goodsCategoryService.list(null);
        return list.stream().collect(Collectors.toMap(GoodsCategoryDO::getId, Function.identity()));
    }

    public List<GoodsCategoryDO> getCategoriesDoByBusinessId(Integer businessId) {
        Map<Integer, GoodsCategoryDO> cache = super.getCache();
        return cache.values().stream().filter(e -> e.getBusinessId().equals(businessId)).collect(Collectors.toList());
    }

    /**
     * 获取对应的供应商
     *
     * @param categoryIds 类目id合集
     * @return 返回 businessId:List<GoodsCategoryDO>
     */
    public Map<Integer, List<GoodsCategoryDO>> toBusinessList(List<Integer> categoryIds) {
        Map<Long, List<GoodsCategoryDO>> map = Maps.newHashMap();
        Map<Integer, GoodsCategoryDO> cache = getCache();
        List<GoodsCategoryDO> categoryDOList = categoryIds.stream().map(cache::get).collect(Collectors.toList());
        return categoryDOList.stream().collect(Collectors.groupingBy(GoodsCategoryDO::getBusinessId));
    }

    /**
     * 获取 businessId:
     *
     * @param categoryId 类目id
     * @return
     */
    public Integer toBusiness(Integer categoryId) {
        Integer businessId = null;
        Map<Long, List<GoodsCategoryDO>> map = Maps.newHashMap();
        Map<Integer, GoodsCategoryDO> cache = getCache();
        GoodsCategoryDO goodsCategoryDO = cache.get(categoryId);
        if (goodsCategoryDO != null) {
            businessId = goodsCategoryDO.getBusinessId();
        }
        return businessId;
    }
}
