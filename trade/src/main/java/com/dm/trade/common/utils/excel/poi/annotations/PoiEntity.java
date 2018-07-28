package com.dm.trade.common.utils.excel.poi.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * poi实体
 * @author sunlingao@zbj.com
 * @title
 * @date 2017年9月21日
 * @version
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PoiEntity {
	
	/**
	 * 是否以列为标准的实体
	 * @return
	 */
	boolean isColumnEntity();
	
	/**
	 * 是否以行为标准的实体
	 * @return
	 */
	boolean isRowEntity();

}
