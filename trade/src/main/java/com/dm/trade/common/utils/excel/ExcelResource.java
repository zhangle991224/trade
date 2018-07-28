//package com.dm.trade.common.utils.excel;
//
//import com.alibaba.fastjson.JSON;
//import com.dm.trade.common.utils.excel.poi.builder.WorkBookBuilder;
//import org.apache.poi.ss.usermodel.Workbook;
//
//import java.io.File;
//import java.net.URL;
//import java.util.List;
//
///**
// * @author zhongchao
// * @title ExcelResource.java
// * @Date 2018-05-24
// * @since v1.0.0
// */
//public class ExcelResource {
//    URL resource = ExcelResource.class.getResource("wmyl.xlsx");
//    Workbook book = WorkBookBuilder.create(new File(resource.getPath())).build();
//    List<Entity> list = ImportAnalyseHandler.analyseEntityExcel(book, 100, 0, true, 0, Entity.class);
//        System.out.println(JSON.toJSONString(list));
//}
