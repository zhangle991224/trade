//package com.dm.trade;
//
//import com.alibaba.fastjson.JSON;
//import com.dm.trade.common.utils.BeanUtils;
//import com.dm.trade.common.utils.excel.poi.analyse.ImportAnalyseHandler;
//import com.dm.trade.common.utils.excel.poi.builder.WorkBookBuilder;
//import com.dm.trade.goods.domain.GoodsDO;
//import com.dm.trade.goods.service.GoodsService;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author zhongchao
// * @title BaseTest.java
// * @Date 2018-05-24
// * @since v1.0.0
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = BootdoApplication.class)
//public class BaseTest {
//
//    @Resource
//    private GoodsService goodsService;
//
//    @Test
//    public void excel() {
//        String path = this.getClass().getClassLoader().getResource("").getPath();
//
//        Workbook book = WorkBookBuilder.create(new File(path + "/bj1.xlsx")).build();
//        List<GoodsExcel> list = ImportAnalyseHandler.analyseEntityExcel(book, 225, 0, true, 0, GoodsExcel.class);
//        list.forEach(e -> {
//
//            String containerPrices = e.getContainerPrices();
//            if (containerPrices != null) {
//                e.setContainerPrice((int) (Double.valueOf(String.valueOf(containerPrices)) * 100));
//            }
//            String bulkPrices = e.getBulkPrices();
//            if (bulkPrices != null) {
//                e.setBulkPrice((int) (Double.valueOf(String.valueOf(bulkPrices)) * 100));
//            }
////            if ()
//
//           /* Integer num = 1;
//            if (StringUtils.isNotBlank(specifications)) {
//                try {
//                    System.out.println(specifications);
//                    num = Integer.valueOf(specifications.substring(specifications.indexOf("*") + 1, specifications.length()));
//                } catch (Exception e2) {
//                    e2.printStackTrace();
//                }
//            }
//            if (e.getBulkPrice() != null) {
//                e.setContainerPrice((int) (Double.valueOf(String.valueOf(e.getBulkPrice())) * 100) * num);
//            }*/
//
//        });
//        List<GoodsDO> collect = list.stream().map(e -> BeanUtils.copyProperties(e, GoodsDO.class)).collect(Collectors.toList());
//        list.forEach(e -> {
//            GoodsDO goodsDO = BeanUtils.copyProperties(e, GoodsDO.class);
//            goodsDO.setCategoryId(4);
//            goodsService.save(goodsDO);
//        });
//        System.out.println(JSON.toJSONString(list));
//    }
//}
