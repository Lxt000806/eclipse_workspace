package com.house.framework.commons.cache;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.entity.Dict;
import com.house.framework.entity.DictItem;

/**
 * 字典缓存工具类
 *
 */
public class DictCacheUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DictCacheUtil.class);

	/**
	 * 根据字典编码获取字典对象
	 * @param dictCode  字典编码
	 * @return 字典对象
	 */
	public static Dict getByDictCode(String dictCode){
		if(StringUtils.isBlank(dictCode))
			return null;
		Dict dict = (Dict)getDictCacheManager().get(dictCode.trim());
		if(dict == null)
			logger.warn("缓存中不存在 字典编码为 ["+dictCode+"] 的字典");
		return dict;
	}
	
	/**
	 * 根据字典编码获取所有的字典元素列表
	 * @param dictCode
	 * @return 字典元素列表
	 */
	public static List<DictItem> getItemsByDictCode(String dictCode) {
		Dict dict = getByDictCode(dictCode);
		if(dict == null)
			return null;
		List<DictItem> list = dict.getDictItems();
		if(list == null || list.size() < 1){
			logger.warn("缓存中字典编码 ["+dictCode+"] 的字典元素为空");
		}
		return list;
	}
	
	/**
	 * 根据字典值获取字典元素
	 * @param dictCode
	 * @param lableValue
	 * @return 字典元素
	 */
	public static DictItem getDictLableByDictCodeAndLableValue(String dictCode,String lableValue) {
		List<DictItem> list = getItemsByDictCode(dictCode);
		if(list == null || list.size() < 1)
			return null;
		for(DictItem dictItem : list){
			if(lableValue.trim().equals(dictItem.getItemValue()))
				return dictItem;
		}
		if(list == null || list.size() < 1){
			logger.warn("缓存中字典编码 ["+dictCode+"] 的字典元素为空");
		}
		return null;
	}
	
	/**
	 * 根据字典编码或元素编码获取字典元素对象
	 * 
	 * @param dictCode  字典编码
	 * @param itemCode  元素编码
	 * @return 字典元素对象
	 */
	public static DictItem getByDictCodeAndItemCode(String dictCode, String itemCode){
		if(StringUtils.isBlank(itemCode))
			return null;
		List<DictItem> list = getItemsByDictCode(dictCode);
		if(list == null || list.size() < 1)
			return null;
		for(DictItem dictItem : list){
			if(itemCode.trim().equals(dictItem.getItemValue()) || itemCode.trim().equals(dictItem.getItemCode()))
				return dictItem;
		}
		logger.warn("缓存中字典编码 ["+dictCode+"] 字典元素编码 ["+itemCode+"] 的字典元素不存在");
		return null;
	}
	
	/**
	 * 根据字典编码或元素编码获取字典元素名称
	 * @param dictCode  字典编码
	 * @param itemCode  元素编码
	 * @return 字典元素名称
	 */
	public static String getItemLabel(String dictCode, String itemCode){
		DictItem dictItem = getByDictCodeAndItemCode(dictCode, itemCode);
		if(dictItem == null)
			return null;
		String label = dictItem.getItemLabel();
		if(StringUtils.isBlank(label)){
			logger.warn("缓存中字典编码 ["+dictCode+"] 字典元素编码 ["+itemCode+"] 的字典元素的文本值为空");
		}
		return label;
	}
	
	/**
	 * 根据字典编码或元素编码获取字典元素值
	 * @param dictCode  字典编码
	 * @param itemCode  元素编码
	 * @return 字典元素值
	 */
	public static String getItemValue(String dictCode, String itemCode){
		DictItem dictItem = getByDictCodeAndItemCode(dictCode, itemCode);
		if(dictItem == null)
			return null;
		String value = dictItem.getItemValue();
		if(StringUtils.isBlank(value)){
			logger.warn("缓存中字典编码 ["+dictCode+"] 字典元素编码 ["+itemCode+"] 的字典元素的文本值为空");
		}
		return value;
	}
	
	/**
	 * 获取字典管理器
	 * @return
	 */
	private static DictCacheManager getDictCacheManager(){
		DictCacheManager dictCacheManager = (DictCacheManager)SpringContextHolder.getBean("dictCacheManager");
		return dictCacheManager;
	}
	
	/**
	 * 根据数据类型，取得字典编码或元素编码获取字典元素值
	 * 
	 *@param <T>
	 *@param dictCode
	 *@param itemCode
	 *@param clazz
	 *@return T
	 */
	public static <T extends Number> T getItemValue(String dictCode, String itemCode, Class<T> clazz){
		String value = getItemValue(dictCode, itemCode);
        if (value == null || clazz == null) return null;
        return CurrentUtil.numberFormat(value, clazz);
	}
	
	/**
	 * 根据数据类型，取得字典编码或元素编码获取字典元素值
	 * 
	 *@param <T>
	 *@param dictCode
	 *@param itemCode
	 *@param clazz
	 *@return T
	 */
	public static <T extends Number> T getItemValue(String dictCode, String itemCode, Class<T> clazz, String defaultValue){
		String value = getItemValue(dictCode, itemCode);
		if (defaultValue != null && !defaultValue.equals("")) {
			if (value == null || clazz == null) 
				return CurrentUtil.numberFormat(defaultValue, clazz);;
			return CurrentUtil.numberFormat(value, clazz);
		} else {
			return null;
		}
	}
	
	/**
	 * 根据字典值获取字典元素
	 * @param dictCode
	 * @param lableValue
	 * @return 字典元素
	 */
	public static String getDictItemLable(String dictCode,String lableValue) {
		List<DictItem> list = getItemsByDictCode(dictCode);
		if(list == null || list.size() < 1)
			return null;
		for(DictItem dictItem : list){
			if(lableValue.trim().equals(dictItem.getItemValue()))
				return dictItem.getItemLabel();
		}
		return null;
	}
	/**
	 * 根据字典编码或元素值获取字典元素对象
	 * 
	 * @param dictCode  字典编码
	 * @param itemValue  元素值
	 * @return 字典元素对象
	 */
	public static DictItem getByDictCodeAndItemValue(String dictCode, String itemValue){
		if(StringUtils.isBlank(itemValue))
			return null;
		List<DictItem> list = getItemsByDictCode(dictCode);
		if(list == null || list.size() < 1)
			return null;
		for(DictItem dictItem : list){
			if(itemValue.trim().equals(dictItem.getItemValue()))
				return dictItem;
		}
		logger.warn("缓存中字典编码 ["+dictCode+"] 字典元素值["+itemValue+"] 的字典元素不存在");
		return null;
	}
}
