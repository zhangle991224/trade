package com.dm.trade.common.utils.excel.poi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标明字段是否为必填
 * @author sunlingao@zbj.com
 * @title
 * @date 2017年10月18日
 * @version
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PoiFieldIsRequire {
	
}
