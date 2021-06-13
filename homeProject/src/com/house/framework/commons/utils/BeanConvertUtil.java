package com.house.framework.commons.utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.house.framework.commons.converters.BeanUtilsEx;

public class BeanConvertUtil {
	public static <T> List<T> beanToBeanList(
			List<?> srcList, Class<T> beanClass) {
		List<T> list = new ArrayList<T>();
		for (Object obj : srcList) {
			try {
				T t = beanClass.newInstance();
				BeanUtilsEx.copyProperties(t, obj);
				list.add(t);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public static <T> List<T> mapToBeanList(
			List<Map<String, Object>> srcMapList, Class<T> beanClass) {
		List<T> list = new ArrayList<T>();
		for (Map<String, Object> map : srcMapList) {
			try {
				list.add(mapToBean(map, beanClass.newInstance()));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public static <T> T mapToBean(Map<String, Object> srcMap, T entity) {
		if (srcMap==null){
			return entity;
		}
		try {
			//获取类所有属性
			Field[] fields = entity.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				resetMap(srcMap,fields[i].getName());
			}
			BeanUtilsEx.copyProperties(entity, srcMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	public static void resetMap(Map<String,Object> srcMap, String fieldName){
		if (srcMap==null || StringUtils.isBlank(fieldName)){
			return;
		}
		String map_key = null;
		Object map_value = null;
		for (Map.Entry<String, Object> entry : srcMap.entrySet()){
			String key = entry.getKey();
			if (fieldName.equalsIgnoreCase(key)){
				map_key = fieldName;
				map_value = entry.getValue();
			}else{
				if (fieldName.equalsIgnoreCase(key.replaceAll("_", ""))){
					map_key = fieldName;
					map_value = entry.getValue();
				}
			}
		}
		if (StringUtils.isNotBlank(map_key)){
			if (map_value!=null && map_value instanceof String){
				map_value = ((String)map_value).trim();
			}
			srcMap.put(map_key, map_value);
		}
	}
	public static List<Map<String, Object>> resultSetToList(ResultSet rs) throws java.sql.SQLException {   
        if (rs == null)   
            return Collections.emptyList();   
        ResultSetMetaData md = rs.getMetaData(); //得到结果集的结构信息，比如字段数、字段名等   
        int columnCount = md.getColumnCount(); //返回此 ResultSet对象中的列数   
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> rowData = new LinkedHashMap<String, Object>(); 
        while (rs.next()) {   
         rowData = new LinkedHashMap<String, Object>();   
         for (int i = 1; i <= columnCount; i++) {   
        	 rowData.put(StringUtils.lowerCase(md.getColumnName(i)), rs.getObject(i));   
         }   
         list.add(rowData);    
        }   
        return list;   
	} 
}
