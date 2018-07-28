//package com.dm.trade.common.utils.excel.poi.builder;
//
//
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//
////import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
///**
// * 工作薄构建器
// * @author sunlingao@zbj.com
// * @title
// * @date 2017年9月21日
// * @version
// */
//public final class WorkBookBuilder {
//
//	private static final Logger log = LoggerFactory.getLogger(WorkBookBuilder.class);
//
//	private WorkBookBuilder(){}
//
//	public static class Builder {
//
//		private InputStream stream;
//
//		private String fileName;
//
//		public Builder(InputStream stream, String name) {
//			this.stream = stream;
//			this.fileName = name;
//		}
//
//		/**
//		 * 构建工作薄
//		 *
//		 * @param stream
//		 *            文件流
//		 * @param filename
//		 *            文件名
//		 * @return
//		 */
//		public Workbook build() {
//			if (stream == null || StringUtils.isBlank(fileName))
//				throw new NullPointerException("stream is null or filename is null");
//			try {
//				fileName = StringUtils.lowerCase(fileName);
//				if (fileName.endsWith(Constants.XLS))
//					return new HSSFWorkbook(stream);
//				if (fileName.endsWith(Constants.XLSX))
//					return new XSSFWorkbook(stream);
//			} catch (Exception e) {
//				log.error("builderWorkBook", e);
//				return null;
//			} finally {
//				try {
//					stream.close();
//				} catch (IOException e) {
//					log.error("stream close", e);
//				}
//			}
//			return null;
//		}
//	}
//
//	public static Builder create(File file) {
//		try {
//			return new Builder(FileUtils.openInputStream(file), file.getName());
//		} catch (IOException e) {
//			log.error("builder create", e);
//			return null;
//		}
//	}
//
//	public static Builder create(InputStream stream,String fileName){
//			return new Builder(stream, fileName);
//	}
//
//}
