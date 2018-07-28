package com.dm.trade.common.utils.excel.poi.analyse;


import com.dm.trade.common.utils.excel.poi.annotations.PoiEntity;
import com.dm.trade.common.utils.excel.poi.annotations.PoiFieldIndex;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.util.Map;


/**
 * 保存字段和行(列)关系容器
 * @author sunlingao@zbj.com
 * @title
 * @date 2017年9月21日
 * @version
 */
final class FieldIndexContainer {
	
	private FieldIndexContainer(){}

	/**
	 * 保存以列为实体index关系
	 */
	protected static final Map<Class<?>, Map<Integer, Field>> COLUMN_MAP = Maps.newConcurrentMap();

	/**
	 * 保存以行为实体index关系
	 */
	protected static final Map<Class<?>, Map<Integer, Field>> ROW_MAP = Maps.newConcurrentMap();

	/**
	 * 根据下标找到映射字段
	 * 
	 * @param index
	 *            字段所在下标
	 * @param cls
	 *            反射类
	 * @return
	 */
	public static <T> Field findFieldWithClass(int index, Class<T> cls) {
		Map<Integer,Field> rel = initIndexField(cls);
		if(rel != null){
			return rel.get(index);
		}
		return null;
	}

	private static Map<Integer,Field> initIndexField(Class<?> cls) {
		PoiEntity entity = cls.getAnnotation(PoiEntity.class);
		if (entity != null && entity.isColumnEntity()) {
			fullContainer(COLUMN_MAP, cls);
			return COLUMN_MAP.get(cls);
		} else if (entity != null && entity.isRowEntity()) {
			fullContainer(ROW_MAP, cls);
			return ROW_MAP.get(cls);
		}
		return null;
	}

	/**
	 * 填充容器
	 * @param map
	 * @param cls
	 */
	private static void fullContainer(Map<Class<?>, Map<Integer, Field>> map, Class<?> cls) {
		if (map.get(cls) == null) {
			Field[] fields = cls.getDeclaredFields();
			Map<Integer, Field> rel = Maps.newHashMap();
			for (Field field : fields) {
				PoiFieldIndex indexan = field.getAnnotation(PoiFieldIndex.class);
				if (indexan != null && indexan.index() > -1) {
					rel.put(indexan.index(), field);
				}
			}
			map.put(cls, rel);
		}
	}
}
