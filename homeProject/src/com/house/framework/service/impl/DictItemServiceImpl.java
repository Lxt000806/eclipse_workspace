package com.house.framework.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.house.framework.commons.cache.DictCacheManager;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.Reflections;
import com.house.framework.dao.DictDao;
import com.house.framework.dao.DictItemDao;
import com.house.framework.entity.Dict;
import com.house.framework.entity.DictItem;
import com.house.framework.service.DictItemService;

@Service
public class DictItemServiceImpl implements DictItemService {
	
	private static final Logger logger = LoggerFactory.getLogger(DictItemServiceImpl.class);
	
	@Autowired
	private DictItemDao dictItemDao;
	@Autowired
	private DictDao dictDao;
	@Autowired
	private DictCacheManager dictCacheManager;

	//@LoggerAnnotation(okLog = "添加 ID=$args[0].itemId itemLabel=$args[0].itemLabel 所属字典 dictId=$args[0].dictId  成功", errLog="添加 itemLabel=$args[0].itemLabel 失败",  modelCode = LogModuleConstant.DICT_ITEM_MODULE )
	public void save(DictItem dictItem) {
		Assert.notNull(dictItem.getDictId());
		Assert.notNull(dictItem.getItemCode());
		dictItem.setGenTime(new Date());
		if(dictItem.getStatus() == null)
			dictItem.setStatus(CommonConstant.STATUS_USABLE);
		Reflections.trim(dictItem);
		this.dictItemDao.save(dictItem);
		
		this.asyCache(dictItem, false);
	}
	
	//@LoggerAnnotation(okLog = "更新 ID=$args[0].itemId itemLabel=$args[0].itemLabel 所属字典 dictId=$args[0].dictId  成功", errLog="更新 ID=$args[0].itemId itemLabel=$args[0].itemLabel 失败",  modelCode = LogModuleConstant.DICT_ITEM_MODULE )
	public void update(DictItem dictItem) {
		Assert.notNull(dictItem.getDictId());
		Assert.notNull(dictItem.getItemCode());
		dictItem.setUpdateTime(new Date());
		if(dictItem.getStatus() == null)
			dictItem.setStatus(CommonConstant.STATUS_USABLE);
		Reflections.trim(dictItem);
		this.dictItemDao.update(dictItem);
		
		this.asyCache(dictItem, false);
	}
	
	//@LoggerAnnotation(okLog = "删除 $args[0] 成功",errLog = "删除 $args[0] 成功",modelCode = LogModuleConstant.DICT_ITEM_MODULE)
	public void delete(Long dictItemId) {
		DictItem dictItem = this.get(dictItemId);
		if(dictItem == null){
			logger.debug("字典元素查找不到");
			return ;
		}
		this.dictItemDao.delete(dictItem);
		this.asyCache(dictItem, true);
	}
	
	//@LoggerAnnotation(okLog = "删除 $args[0] 成功",errLog = "删除 $args[0] 成功",modelCode = LogModuleConstant.DICT_ITEM_MODULE)
	public void delete(List<Long> dictItemIds){
		if(dictItemIds == null || dictItemIds.size() < 1)
			return ;
		for (int i = 0; i < dictItemIds.size(); i++) {
			this.delete(dictItemIds.get(i));
		}
	}

	public DictItem get(Long dictItemId) {
		if(dictItemId == null)
			return null;
		return this.dictItemDao.get(DictItem.class, dictItemId);
	}
	
	public DictItem getByDictIdAndItemCode(Long dictId, String itemCode){
		if(dictId == null)
			return null;
		if(StringUtils.isBlank(itemCode))
			return null;
		return this.dictItemDao.getByDictIdAndItemCode(dictId, itemCode);
	}
	
	public DictItem getByDictIdAndLabel(Long dictId, String itemLabel){
		if(dictId == null || StringUtils.isBlank(itemLabel))
			return null;
		return this.dictItemDao.getByDictIdAndLabel(dictId, itemLabel);
	}

	@SuppressWarnings("rawtypes")
	public Page findPage(Page page, DictItem dictItem) {
		return this.dictItemDao.findPage(page, dictItem);
	}

	//@LoggerAnnotation(okLog="#if($args[1] == '1')启用#end#if($args[1] == '0')禁用#end IDS=$args[0] 成功", errLog="#if($args[1] == '1')启用#end#if($args[1] == '0')禁用#end IDS=$args[0] 失败", modelCode=LogModuleConstant.DICT_ITEM_MODULE)
	public void setStatus(Long dictItemId, String status) {
		DictItem dictItem = this.get(dictItemId);
		if(dictItem == null)
			return ;
		dictItem.setStatus(status);
		this.dictItemDao.update(dictItem);
		
		this.asyCache(dictItem, false);
	}
	
	/**
	 * 根据字典编码获取该字典所有字典元素
	 * @param dictCode  字典编码
	 * @return
	 */
	public List<DictItem> getByDictCode(String dictCode) {
		if(StringUtils.isBlank(dictCode))
			return null;
		return this.dictItemDao.getByDictCode(dictCode);
	}
	/**
	 * 根据字典编码获取该字典有效字典元素
	 * @param dictCode  字典编码
	 * @return
	 */
	public List<Integer> getByDictItem(String dictCode) {
		if(StringUtils.isBlank(dictCode))
			return null;
		return this.dictItemDao.getByDictItem(dictCode);
	}
	/**
	 * 根据字典ID获取所有的字典元素列表
	 * @param dictId
	 * @return
	 */
	public List<DictItem> getByDictId(Long dictId,String status){
		if(dictId == null)
			return null;
		return this.dictItemDao.getByDictId(dictId,status);
	}

	/**
	 * 获取下一个排序号
	 * @param dictId
	 * @return
	 */
	public int getNextNum(Long dictId){
		if(dictId == null)
			return 0;
		return this.dictItemDao.getNextNum(dictId);
	}

	@SuppressWarnings(value="unchecked")
	public List<DictItem> getAll() {
		String hql = "from DictItem t order by t.dictId, t.orderNo";
		return this.dictItemDao.find(hql);
	}
	
	private void asyCache(DictItem dictItem, boolean delFlag){
		Assert.notNull(dictItem.getDictId(), "字典ID不能为空");
		Dict dict = this.dictDao.get(Dict.class, dictItem.getDictId());
		Assert.notNull(dict, "字典 ID=["+dictItem.getDictId()+"] 不存在");
		
		Dict cacheDict = (Dict)dictCacheManager.get(dict.getDictCode());
		if(cacheDict == null){
			cacheDict = dict;
			cacheDict.setDictItems(this.dictItemDao.getByDictId(dict.getDictId(),CommonConstant.STATUS_USABLE));
		}
		List<DictItem> dictItems = cacheDict.getDictItems();
		if(dictItems == null){
			dictItems = new ArrayList<DictItem>();
		}
		boolean flag = true;
		for(DictItem item : dictItems) {
			if(item != null && item.getItemId().longValue() == dictItem.getItemId().longValue()){
				if(delFlag){
					dictItems.remove(item);
				}else{
					dictItems.remove(item);
					dictItems.add(dictItem);
				}
				flag = false;
				break;
			}
		}
		if(flag){
			dictItems.add(dictItem);
		}
		cacheDict.setDictItems(dictItems);
	}

	
	public DictItem getDictItemByDictCode(String dictCode) {
		
		return this.dictItemDao.getDictItemByDictCode(dictCode);
	}
}
