package com.dm.trade.goods.dao;

import com.dm.trade.goods.domain.GoodsDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 商品类别表
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-04-01 21:57:40
 */
@Mapper
public interface GoodsDao {

    GoodsDO get(Long id);

    /**
     * 根据商品id列表获取商品信息
     *
     * @param ids
     * @return
     */
    List<GoodsDO> getList(List<Long> ids);

    List<GoodsDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(GoodsDO goods);

    int update(GoodsDO goods);

    int remove(Integer id);

    int batchRemove(Integer[] ids);
}
