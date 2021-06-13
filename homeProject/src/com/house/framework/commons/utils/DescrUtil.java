package com.house.framework.commons.utils;
 
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.house.framework.annotation.DescrAnnotation;
 
public class DescrUtil {
	
	/**
	 * 通过属性取得属性的描述注解
	 * 
	 * @param field
	 * @return
	 */
	public static String getDescr(Field field) {
		String result = null;
		try {
			field.setAccessible(true);
			Annotation[] annotation = field.getAnnotations();
			for (Annotation tag : annotation) {
				if (tag instanceof DescrAnnotation) {
					result = ((DescrAnnotation) tag).descr();
					break;
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return result;
	}
 
	/**
	 * 通过对象和属性名称取得属性的描述注解
	 * 
	 * @param obj
	 * @param propertyName
	 * @return
	 */
	public static String getDescr(Object obj, String propertyName) {
		String result = null;
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getName().equals(propertyName)) {
					String desc = getDescr(field);
					if (desc != null && !desc.isEmpty()) {
						result = desc;
						break;
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return result;
	}
 
	
 
	/**
	 * 取得obj所有属性的描述注解，返回值为key为obj的属性名称,value为此属性的描述注解
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, String> getAllDescr(Object obj) {
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			return getResult(fields);
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 取得obj所有属性的描述注解，返回值为key为obj的属性名称,value为此属性的描述注解
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, String> getAllDescr(String clzName) {
		try {
			Field[] fields = Class.forName(clzName).getDeclaredFields();
			return getResult(fields);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 将field[]里的字段名称做为key和字段描述做value放在map中
	 * 
	 * @param fields
	 * @param map
	 */
	private static Map<String, String> getResult(Field[] fields) {
		Map<String, String> result = new HashMap<String, String>();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getName().equals("id")) {
				continue;
			}
			String desc = getDescr(field);
			if (desc != null && !desc.isEmpty()) {
				result.put(field.getName(), getDescr(field));
			}
		}
		return result;
	}
	
}
