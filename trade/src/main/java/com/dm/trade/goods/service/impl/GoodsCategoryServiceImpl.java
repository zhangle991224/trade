package com.dm.trade.goods.service.impl;

import com.dm.trade.goods.dao.GoodsCategoryDao;
import com.dm.trade.goods.domain.GoodsCategoryDO;
import com.dm.trade.goods.service.GoodsCategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
    @Autowired
    private GoodsCategoryDao goodsCategoryDao;

    @Override
    public GoodsCategoryDO get(Integer id) {
        return goodsCategoryDao.get(id);
    }

    @Override
    public List<GoodsCategoryDO> list(Map<String, Object> map) {
        return goodsCategoryDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return goodsCategoryDao.count(map);
    }

    @Override
    public int save(GoodsCategoryDO goodsCategory) {
        return goodsCategoryDao.save(goodsCategory);
    }

    @Override
    public int update(GoodsCategoryDO goodsCategory) {
        return goodsCategoryDao.update(goodsCategory);
    }

    @Override
    public int remove(Integer id) {
        return goodsCategoryDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return goodsCategoryDao.batchRemove(ids);
    }

    @Override
    public List<GoodsCategoryDO> getCategoriesByIds(List<Integer> ids) {
        List<GoodsCategoryDO> categoryDOS = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(ids)) {
            categoryDOS = goodsCategoryDao.getByIds(ids);
        }
        return categoryDOS;
    }


}
