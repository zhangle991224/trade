package com.dm.trade.goods.service;

import com.dm.trade.goods.domain.GoodsCategoryDO;

import java.util.List;
import java.util.Map;

/**
 * 商品类别表
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-01 21:57:41
 */
public interface GoodsCategoryService {

    GoodsCategoryDO get(Integer id);

    List<GoodsCategoryDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(GoodsCategoryDO goodsCategory);

    int update(GoodsCategoryDO goodsCategory);

    int remove(Integer id);

    int batchRemove(Integer[] ids);

    /**
     * 根据id集合获取类目
     *
     * @param ids
     * @return
     */
    List<GoodsCategoryDO> getCategoriesByIds(List<Integer> ids);
}
