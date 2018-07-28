//package com.dm.trade.common.utils.excel.poi.analyse;
//
//import com.dm.trade.common.utils.excel.poi.annotations.PoiFieldIndex;
//import com.dm.trade.common.utils.excel.poi.builder.Constants;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.List;
//
///**
// * 导出解析处理器
// * @author sunlingao@zbj.com
// * @title
// * @date 2017年9月21日
// * @version
// */
//public final class ExportAnalyseHandler {
//
//	private static final Logger LOG = LoggerFactory.getLogger(ExportAnalyseHandler.class);
//
//	private ExportAnalyseHandler(){}
//
//	/**
//	 * 实体导出成workbook对象
//	 * @author sunlingao@zbj.com
//	 * @date 2017年9月21日
//	 * @version
//	 * @param list
//	 * @param titles
//	 * @return
//	 */
//	public static <T> Workbook exportListToBook(List<T> list, String[] titles){
////		if(list!=null && list.size() > Constants.MAX_SIZE){
////			LOG.warn("exportListToBook list size is {} too large titles is {}", list.size(),JSON.toJSONString(titles));
////			return null;
////		}
//		/** row移动下标 */
//		int index = 0;
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		HSSFSheet sheet = workbook.createSheet();
//		if(titles != null && titles.length > 0){
//			/** 生成每行标题 */
//			HSSFRow row = sheet.createRow(0);
//			for(int i=0;i<titles.length;i++){
//				HSSFCell cell = row.createCell(i);
//				cell.setCellValue(titles[i]);
//			}
//			index = 1;
//		}
//		if(CollectionUtils.isEmpty(list)){
//			LOG.warn("实体数据为空!");
//			return workbook;
//		}
//		/** 填充数据 */
//		for(int i=0;i<list.size(); i++){
//			if(i>= Constants.MAX_SIZE)break;//超过最大导出行数
//			HSSFRow row = sheet.createRow(index++);
//			fillCell(row,list.get(i));
//		}
//		return workbook;
//	}
//
//	public static <T> Workbook exportListToBook(List<T> list){
//		return exportListToBook(list,null);
//	}
//
//	/**
//	 * 填充单元格
//	 * @author sunlingao@zbj.com
//	 * @date 2017年9月21日
//	 * @version
//	 * @param obj
//	 */
//	private static void fillCell(HSSFRow row, Object obj){
//		int cellCount = 0;
//		Field[] fields = obj.getClass().getDeclaredFields();
//		for(Field field : fields){
//			PoiFieldIndex aon = field.getAnnotation(PoiFieldIndex.class);
//			if(aon != null){
//				field.setAccessible(true);
//				HSSFCell cell = row.createCell(cellCount);
//				String fieldName = field.getName();
//				String getMethodName = "get" + StringUtils.capitalize(fieldName);
//				try{
//				Method method = obj.getClass().getMethod(getMethodName);
//				String cellValue=String.valueOf(method.invoke(obj));
//				cell.setCellValue("null".equals(cellValue)?"":cellValue);
//				}catch(Exception e){
//					LOG.error("fillCell error method is {} msg is {}", getMethodName,e);
//					cell.setCellValue("");
//				}
//				cellCount++;
//			}
//		}
//	}
//
//}
