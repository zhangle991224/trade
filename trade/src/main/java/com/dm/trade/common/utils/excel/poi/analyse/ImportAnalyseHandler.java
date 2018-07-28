//package com.dm.trade.common.utils.excel.poi.analyse;
//
//
//import com.dm.trade.common.utils.excel.poi.annotations.PoiEntity;
//import com.dm.trade.common.utils.excel.poi.annotations.PoiFieldIsRequire;
//import com.google.common.collect.Lists;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.Iterator;
//import java.util.List;
//
//
///**
// * 导入解析处理器
// * @author sunlingao@zbj.com
// * @title
// * @date 2017年9月21日
// * @version
// */
//public final class ImportAnalyseHandler {
//
//	private static final Logger LOG = LoggerFactory.getLogger(ImportAnalyseHandler.class);
//
//	private ImportAnalyseHandler(){}
//
//	/**
//	 * 解析xls 并生成对应对象
//	 *
//	 * @date:2017年4月27日
//	 * @param book
//	 *            xls对象
//	 * @param cls
//	 * @return List<T>
//	 */
//	public static <T> List<T> analyseEntityExcel(Workbook book, Class<T> cls) throws Exception {
//		return analyseEntityExcel(book, -1, 0, false, 0, cls);
//	}
//
//	/**
//	 * 解析xls 并生成对应对象
//	 *
//	 * @param book
//	 *            工作薄
//	 * @param sheetIndex
//	 *            sheet下标
//	 * @param cls
//	 * @return
//	 */
//	public static <T> List<T> analyseEntityExcel(Workbook book, int sheetIndex, Class<T> cls) {
//		return analyseEntityExcel(book, -1, 0, false, sheetIndex, cls);
//	}
//
//	/**
//	 * 解析xls 并生成对应对象
//	 *
//	 * @param book
//	 *            工作薄
//	 * @param maxNum
//	 *            解析最大数
//	 * @param startCell
//	 *            开始单元格
//	 * @param skipFirstRow
//	 *            是否跳过第一行
//	 * @param sheetIndex
//	 *            sheet下标
//	 * @param cls
//	 * @return
//	 */
//	public static <T> List<T> analyseEntityExcel(Workbook book, int maxNum, int startCell, boolean skipFirstRow,
//                                                 int sheetIndex, Class<T> cls) {
//		PoiEntity entity = cls.getAnnotation(PoiEntity.class);
//		if (entity == null)
//			return Lists.newArrayList();
//		List<T> result = null;
//		Sheet sheet = book.getSheetAt(sheetIndex);
//		try {
//			if (entity.isColumnEntity()) {
//				result = analyseColumn(sheet, maxNum, startCell, skipFirstRow, cls);
//			} else if (entity.isRowEntity()) {
//				result = analyseRow(sheet, maxNum, startCell, skipFirstRow, cls);
//			}
//		} catch (Exception e) {
//			LOG.error("analyseEntityExcel", e);
//			return Lists.newArrayList();
//		}
//		return result;
//	}
//
//	private static <T> List<T> analyseRow(Sheet sheet, int maxNum, int startCell, boolean skipFirstRow, Class<T> cls)
//			throws Exception {
//		int rowNum = sheet.getLastRowNum();
//		maxNum = maxNum > -1 ? (maxNum > rowNum ? rowNum : maxNum) : rowNum;
//		Iterator<Row> rowList = sheet.rowIterator();
//		List<T> result = Lists.newArrayList();
//		int rowIndex = 0;
//		while (rowList.hasNext()) {
//			if (rowIndex > maxNum)
//				break;
//			Row row = rowList.next();
//			if (rowIndex == 0 && skipFirstRow){
//				rowIndex++;
//				continue;
//			}
//			T obj = cls.newInstance();
//			Iterator<Cell> cellList = row.cellIterator();
//			int cellIndex = 0;
//			while (cellList.hasNext()) {
//				Cell cell = cellList.next();
//				if (cellIndex < startCell){
//					cellIndex++;
//					continue;
//				}
//				obj = fillObjForCell(cell, cellIndex, obj);
//				cellIndex++;
//				if(obj == null)
//					break;
//			}
//			if(obj != null)
//				result.add(obj);
//			rowIndex++;
//		}
//		return result;
//	}
//
//	private static <T> List<T> analyseColumn(Sheet sheet, int maxNum, int startCell, boolean skipFirstRow, Class<T> cls)
//			throws Exception {
//		int columnNum = sheet.getRow(0).getPhysicalNumberOfCells();
//		int rowNum = sheet.getLastRowNum();
//		maxNum = maxNum > -1 ? (maxNum > columnNum ? columnNum : maxNum) : columnNum;
//		List<T> result = Lists.newArrayList();
//		for (int m = 0; m < maxNum; m++) {
//			T obj = cls.newInstance();
//			for (int i = 0; i < rowNum; i++) {
//				if(i == 0 && skipFirstRow)
//					continue;
//				Row row = sheet.getRow(i);
//				Cell cell = row.getCell(m + startCell);
//				obj = fillObjForCell(cell, i , obj);
//				if(obj == null)
//					break;
//			}
//			if(obj != null)
//				result.add(obj);
//		}
//
//		return result;
//	}
//
//	/**
//	 * 根据单元数据填充对象
//	 * @author sunlingao@zbj.com
//	 * @date 2017年10月18日
//	 * @version
//	 * @param cell
//	 * @param index
//	 * @param obj
//	 * @return 返回实体引用
//	 */
//	private static <T> T fillObjForCell(Cell cell, int index, T obj) {
//		Field field = FieldIndexContainer.findFieldWithClass(index, obj.getClass());
//		if (field == null || cell == null)
//			return obj;
//		Class<?> fieldType = field.getType();
//		try {
//			Object value = null;
//			cell.setCellType(Cell.CELL_TYPE_STRING);
//			/** 只有当field type 是String 但celltype是int long 类型的时候才转换 */
//			if(fieldType == String.class && cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
//				value = String.valueOf(cell.getNumericCellValue());
//			}else if (fieldType == String.class && cell.getCellType() == Cell.CELL_TYPE_STRING) {
//				value = cell.getStringCellValue();
//			} else if ( (fieldType == Integer.class || fieldType == Long.class || fieldType == long.class
//					|| fieldType == int.class) && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//				value = cell.getNumericCellValue();
//			} else if( (fieldType == Integer.class || fieldType == Long.class || fieldType == long.class
//					|| fieldType == int.class) && cell.getCellType() == Cell.CELL_TYPE_STRING){
//			    if(fieldType == Integer.class || fieldType == int.class){
//			        value = Integer.valueOf(cell.getStringCellValue());
//                }else{
//                    value = Long.valueOf(cell.getStringCellValue()).longValue();
//                }
//			}
//			String fieldName = field.getName();
//			String setMethodName = "set" + StringUtils.capitalize(fieldName);
//			Method method = obj.getClass().getMethod(setMethodName, fieldType);
//			if (method != null && value != null) {
//				method.invoke(obj, value);
//			} else {
//				PoiFieldIsRequire require = field.getAnnotation(PoiFieldIsRequire.class);
//				if(require!=null){
//					obj = null;
//					LOG.warn("obj is {} be set null because field is {} is require but value is null",obj.getClass().getSimpleName(),fieldName);
//				}
//				LOG.warn("method is {} not found or value is null. cls is {}", setMethodName,obj.getClass().getSimpleName());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			LOG.error("fillObjForCell index is {} and name is {} msg is {}",new Object[]{index,field.getName(), e});
//		}
//		return obj;
//	}
//}
