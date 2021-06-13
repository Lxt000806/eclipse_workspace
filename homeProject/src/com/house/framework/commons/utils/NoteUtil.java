package com.house.framework.commons.utils;
 
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.house.framework.annotation.NoteAnnotation;
import com.house.framework.commons.cache.BuilderCacheManager;
import com.house.framework.commons.cache.CustTypeCacheManager;
import com.house.framework.commons.cache.XtdmCacheManager;
import com.house.framework.commons.orm.BaseService;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Xtdm;
import com.house.home.service.design.ResrCustService;

public class NoteUtil {
	
	/**
	 * 通过属性取得属性的注解
	 * 
	 * @param field
	 * @return
	 */
	public static Map<String, String> getNoteMap(Field field) {
		Map<String, String> map=new HashMap<String, String>();
		try {
			field.setAccessible(true);
			Annotation[] annotation = field.getAnnotations();
			for (Annotation tag : annotation) {
				if (tag instanceof NoteAnnotation) {
					map.put("tableName", ((NoteAnnotation) tag).tableName());
					map.put("code", ((NoteAnnotation) tag).code());
					map.put("note", ((NoteAnnotation) tag).note());
					break;
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return map;
	}
 
	/**
	 * 取得属性值的中文名称
	 * 
	 * @param obj
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static String getNoteByValue(Object obj, String propertyName, String value) {
		if(StringUtils.isBlank(value)){
			return "";
		}
		Map<String, String> map = null;
		String note="";
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getName().equals(propertyName)) {//有找到配置的，进行匹配中文名称
					map = getNoteMap(field);
					if(map.containsKey("tableName")){
						note =getNote(map.get("tableName").toString(),map.get("code").toString(),value,map.get("note").toString());
						return note;
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return value;//没找到配置，返回原来的值
	}
	
	/**
	 * 获取中文名称
	 * @param tableName
	 * @param code
	 * @param value
	 * @param note
	 * @return
	 */
	public static String getNote(String tableName,String code,String value,String note){
		String cache=getNoteFromCache(tableName, code, value);
		if(StringUtils.isNotBlank(cache)){
			return cache;
		}
		return getNoteFromDb(tableName, code, value, note);
		
	}
	
	/**
	 * 有缓存的从缓存取
	 * @param tableName
	 * @param code
	 * @param value
	 * @return
	 */
	public static String getNoteFromCache(String tableName,String code,String value){
		if("tBuilder".equals(tableName)){
			BuilderCacheManager builderCacheManager = (BuilderCacheManager)SpringContextHolder.getBean("builderCacheManager");
	        Builder builder=(Builder)builderCacheManager.get(value);
	        return builder.getDescr();
		}else if("tXTDM".equals(tableName)){
			 XtdmCacheManager xtdmCacheManager=(XtdmCacheManager)SpringContextHolder.getBean("xtdmCacheManager");
		     Xtdm xtdm=(Xtdm)xtdmCacheManager.get(code+"_"+value.trim());
		     return xtdm.getNote();
		}else if("tCusttype".equals(tableName)){
			CustTypeCacheManager custTypeCacheManager = (CustTypeCacheManager)SpringContextHolder.getBean("custTypeCacheManager");
			CustType custType=(CustType)custTypeCacheManager.get(value);
	        return custType.getDesc1();
		}
		
		return "";
		
	}
	
	/**
	 * 没有缓存的从数据库查
	 * @param tableName
	 * @param code
	 * @param value
	 * @param note
	 * @return
	 */
	public static String getNoteFromDb(String tableName,String code,String value,String note){
		ResrCustService resrCustService=(ResrCustService)SpringContextHolder.getBean(ResrCustService.class);
		String sql="select "+note+" note from "+tableName +" where "+code+"= ? ";
		List<Map<String, Object>> list = resrCustService.findBySql(sql,new Object[]{value});
		if(list!=null && list.size()>0){
			return list.get(0).get("note").toString();
		}
		return "";
		
	}
 
	
}
