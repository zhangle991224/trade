package com.dm.trade.common.utils;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Filename BeanUtils.java
 * @Description Bean转换 1.增加日期转换支持 1.对于Integer Byte的封装类型转换会赋默认值修改
 * @Version 1.0
 * @Author zhongc
 * @Email zhong_ch@foxmail.com
 * @History <li>Author: zhongc</li>
 * <li>Date: 2015年7月14日</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class BeanUtils {
    static {
        ConvertUtils.register(new DateConvert(), java.util.Date.class);
        ConvertUtils.register(new DateConvert(), java.sql.Date.class);
        ConvertUtils.register(new NullConvert(), Integer.class);
        ConvertUtils.register(new NullConvert(), Byte.class);
        ConvertUtils.register(new NullConvert(), Long.class);
        ConvertUtils.register(new NullConvert(), Double.class);
        ConvertUtils.register(new NullConvert(), Float.class);
    }

    /**
     * bean 转换
     *
     * @param obj      数据源
     * @param objClass 目的bean
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T copyProperties(Object obj, Class<T> objClass) {
        T t = null;
        if (obj == null) {
            return null;
        }
        try {
            Class<?> c = Class.forName(objClass.getName());
            t = (T) c.newInstance();
            org.apache.commons.beanutils.BeanUtils.copyProperties(t, obj);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return t;
    }

}

class DateConvert implements Converter {

    @Override
    public Object convert(Class type, Object value) {
        if (value == null) {
            return null;

        }
        String changed = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            changed = df.format(value);
            return df.parse(changed);
        } catch (Exception e) {

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            changed = df.format(value);
            try {
                return df.parse(changed);
            } catch (ParseException e1) {
                return null;
            }

        }

    }
}

class NullConvert implements Converter {

    @Override
    public Object convert(Class aClass, Object value) {
        if (value == null) {
            return null;
        }
        return value;
    }
}
