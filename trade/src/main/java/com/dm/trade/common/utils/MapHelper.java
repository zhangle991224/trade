package com.dm.trade.common.utils;

import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MapHelper<T, K, V> {
    /**
     * 将 Bean 转化为Map
     *
     * @param obj Bean
     * @return Map
     * @throws IntrospectionException
     */
    @SuppressWarnings("unchecked")
    public Map<String, V> putBeansToMap(T obj) {
        Map<String, V> map = new HashMap<>();
        try {
            BeanInfo bean = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] pro = bean.getPropertyDescriptors();
            for (PropertyDescriptor proBean : pro) {
                String key = proBean.getName();
                if (!"class".equals(key)) {
                    Method getMet = proBean.getReadMethod();
                    try {
                        Object invoke = getMet.invoke(obj);
                        V value;
                        if (invoke == null) {
                            continue;
                        }
                        value = (V) invoke;
                        map.put(key, value);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    }
                }

            }
        } catch (IntrospectionException exception) {
            exception.printStackTrace();
        }

        return map;
    }

    /**
     * 将Map转换为Bean
     *
     * @param map 有参数的Map
     * @param obj BeanT
     */
    @SuppressWarnings("unchecked")
    public T putMapToBeans(Map<K, V> map, Class<?> obj) {
        if (map.isEmpty()) {
            throw new IllegalArgumentException("Map 不能为空");
        }
        T t = null;
        try {
            Class<?> aClass = Class.<T>forName(obj.getName());
            t = (T) aClass.newInstance();
            BeanUtils.copyProperties(obj, map);

        } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {

        }
        return t;
    }

}
