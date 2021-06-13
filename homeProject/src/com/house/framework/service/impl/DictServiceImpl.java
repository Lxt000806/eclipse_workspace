package com.house.framework.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.cache.DictCacheManager;
import com.house.framework.commons.cache.DictCacheUtil;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.Reflections;
import com.house.framework.dao.DictDao;
import com.house.framework.dao.DictItemDao;
import com.house.framework.entity.Dict;
import com.house.framework.entity.DictItem;
import com.house.framework.service.DictService;
/**
 * 字典service
 */
@Service("dictService")
public class DictServiceImpl implements DictService {
	
	private static final Logger logger = LoggerFactory.getLogger(DictServiceImpl.class);

	@Autowired
	private DictDao dictDao;
	@Autowired
	private DictItemDao dictItemDao;
	@Autowired
	private DictCacheManager dictCacheManager;
	/**
	 * 保存方法
	 */
	//@LoggerAnnotation(okLog = "添加 ID=$args[0].dictId dictName=$args[0].dictName 成功", errLog="添加 $args[0].dictName 失败",  modelCode = LogModuleConstant.DICT_MODULE )
	public void save(Dict dict) {
		if(dict.getStatus() == null)
			dict.setStatus(CommonConstant.STATUS_USABLE);
		dict.setGenTime(new Date());
		Reflections.trim(dict);
		this.dictDao.save(dict);
		
		//刷缓存记录
		dictCacheManager.put(dict.getDictCode(), dict);
	}
	
	/**
	 * 更新字典
	 * @param menu
	 */
	//@LoggerAnnotation(okLog = "更新 ID=$args[0].dictId $args[0].dictName 成功", errLog="更新 ID=$args[0].dictId $args[0].dictName 失败",  modelCode = LogModuleConstant.DICT_MODULE )
	public void update(Dict dict){
		if(dict.getStatus() == null)
			dict.setStatus(CommonConstant.STATUS_USABLE);
		dict.setUpdateTime(new Date());
		Reflections.trim(dict);
		this.dictDao.update(dict);
		
		//刷缓存记录
		List<DictItem> dictItems = DictCacheUtil.getItemsByDictCode(dict.getDictCode());
		dict.setDictItems(dictItems);
		dictCacheManager.put(dict.getDictCode(), dict);
	}
	
	/**
	 * 删除菜单
	 * @param menuId
	 */
	//@LoggerAnnotation(okLog = "删除 $args[0] 成功", errLog="删除 $args[0] 失败",  modelCode = LogModuleConstant.DICT_MODULE )
	public void delete(Long dictId){
		Dict dict = this.get(dictId);
		if(dict == null){
			logger.debug("字典查找不到,删除失效");
			return ;
		}
		this.dictDao.delete(dict);
		List<DictItem> itemList = this.dictItemDao.getByDictId(dictId,null);
		if(itemList != null && itemList.size() > 0){
			for(DictItem dictItem : itemList){
				if(dictItem != null){
					this.dictItemDao.delete(dictItem);
				}
			}
		}
		dictCacheManager.evict(dict.getDictCode());
	}
	
	//@LoggerAnnotation(okLog = "删除 $args[0] 成功", errLog="删除 $args[0] 失败",  modelCode = LogModuleConstant.DICT_MODULE )
	public void delete(List<Long> dictIds){
		if(dictIds == null || dictIds.size() < 1)
			return ;
		for(Long dictId : dictIds){
			this.delete(dictId);
		}
	}
	
	/**
	 * 获取字典对象
	 */
	public Dict get(Long dictId) {
		if(dictId == null)
			return null;
		return this.dictDao.get(Dict.class, dictId);
	}
	
	public Dict getByDictName(String dictName){
		if(StringUtils.isBlank(dictName))
			return null;
		return this.dictDao.getByDictName(dictName.trim());
	}
	
	public Dict getByDictCode(String dictCode){
		if(StringUtils.isBlank(dictCode))
			return null;
		return this.dictDao.getByDictCode(dictCode.trim());
	}
	
	/**
	 * 修改字典状态
	 * @param dictId
	 * @param status
	 */
	//@LoggerAnnotation(okLog="#if($args[1] == '1')启用#end#if($args[1] == '0')禁用#end IDS=$args[0] 成功", errLog="#if($args[1] == '1')启用#end#if($args[1] == '0')禁用#end IDS=$args[0] 失败", modelCode=LogModuleConstant.DICT_MODULE)
	public void setStatus(Long dictId, String status){
		Dict dict = this.get(dictId);
		if(dict == null)
			return ;
		dict.setStatus(status);
		this.update(dict);
	}
	
	@SuppressWarnings(value={ "rawtypes" })
	public Page findPage(Page page, Dict dict) {
		
		return this.dictDao.findPage(page, dict);
	}
	
	/**
	 * 取所有在用字典
	 * @return
	 */
	@SuppressWarnings(value="unchecked")
	public List<Dict> getAll(String status){
		String hql = "from Dict t where 1=1";
		if(StringUtils.isNotBlank(status)){
			hql += " and t.status = ? ";
		}
		hql += " order by t.dictId";
		if(StringUtils.isBlank(status)){
			return this.dictDao.find(hql);
		}else{
			return this.dictDao.find(hql, status);
		}
	}
	
	public List<Dict> getByDictType(String dictType, String status){
		return this.dictDao.getByDictType(dictType, status);
	}
	
}

