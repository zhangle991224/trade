package com.dm.trade;

import com.dm.trade.common.http.HttpHelper;
import com.dm.trade.goods.domain.GoodsDO;
import com.dm.trade.goods.service.GoodsService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author zhongchao
 * @title Test.java
 * @Date 2017-12-17
 * @since v1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootdoApplication.class)
public class Test {

    @Resource
    private GoodsService goodsService;


    public static void main(String[] args) {
        System.out.println( BigDecimal.valueOf(0.03d));
    }

    @org.junit.Test
    public void test(){
        try {
            HttpHelper.get("https://api.weixin.qq.com/sns/jscode2session");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void addGoods(){
        GoodsDO goodsDO = new GoodsDO();
        goodsDO.setId(4L);
        goodsDO.setName("测试商品2");
        goodsDO.setCategoryId(2);
        goodsDO.setContainerPrice(20000);
        goodsDO.setGoodsImg("");
        goodsDO.setBulkPrice(20);
        goodsDO.setBulkNum(20);
        goodsDO.setIsReturn(1);
        goodsDO.setBarcode("12312312");
        goodsDO.setSpecifications("20x20");
        goodsDO.setStatus(1);
        goodsDO.setLabel("我是标签");
        goodsDO.setRemark("我是备注");
        goodsDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
        goodsDO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        goodsService.save(goodsDO);
    }
}
