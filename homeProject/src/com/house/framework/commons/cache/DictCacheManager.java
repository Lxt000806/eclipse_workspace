package com.house.framework.commons.cache;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.entity.Dict;
import com.house.framework.entity.DictItem;
import com.house.framework.service.DictItemService;
import com.house.framework.service.DictService;

@Component
public class DictCacheManager extends SimpleCacheManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DictCacheManager.class);
	
	@Autowired
	private DictService dictService;
	@Autowired
	private DictItemService dictItemService;

	
	public String getCacheKey() {
		return CommonConstant.CACHE_DICT_KEY;
	}

	
	public void initCacheData() {
		logger.debug("###### 初始化 字典 数据开始 ######");
		System.out.println("###### 初始化 字典 数据开始 ######");
		List<Dict> dictList = this.dictService.getAll(CommonConstant.STATUS_USABLE);
		List<DictItem> dictItemList = null;
		for(Dict dict : dictList){
			if(dict != null){
				dictItemList = this.dictItemService.getByDictId(dict.getDictId(),CommonConstant.STATUS_USABLE);
				dict.setDictItems(dictItemList);
				this.springCache.put(dict.getDictCode(), dict);
				System.out.println("缓存字典"+dict.getDictCode());
			}
		}
		logger.debug("###### 初始化 字典 数据结束 ######");
		System.out.println("###### 初始化 字典 数据结束 ######");
	}

	/**
	 * 刷新单条记录
	 * @param key
	 */
	public void refresh(Object key) {
		if(key == null)
			return ;
		String dictCode = key.toString().trim();
		if("".equals(dictCode)){
			return ;
		}
		Dict dict = this.dictService.getByDictCode(dictCode);
		List<DictItem> dictItems = this.dictItemService.getByDictId(dict.getDictId(),CommonConstant.STATUS_USABLE);
		dict.setDictItems(dictItems);
		this.evict(key);
		this.put(key, dict);
	}

}
