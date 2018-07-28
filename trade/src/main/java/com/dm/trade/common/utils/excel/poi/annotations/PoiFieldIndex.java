package com.dm.trade.common.utils.excel.poi.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段与表格中下标对应关系
 * @author sunlingao@zbj.com
 * @title
 * @date 2017年9月21日
 * @version
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PoiFieldIndex{
	
	/**
	 * 字段所对应列的位置 下标从0开始
	 * @return
	 */
	int index() default -1;

}
