package com.house.home.serviceImpl.insales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.BaseItemDao;
import com.house.home.dao.insales.BaseItemType1Dao;
import com.house.home.dao.insales.BaseItemType2Dao;
import com.house.home.entity.insales.BaseItem;
import com.house.home.service.insales.BaseItemService;

@SuppressWarnings("serial")
@Service
public class BaseItemServiceImpl extends BaseServiceImpl implements BaseItemService {

	@Autowired
	private BaseItemDao baseItemDao;
	@Autowired
	private BaseItemType1Dao baseItemType1Dao;
	@Autowired
	private BaseItemType2Dao baseItemType2Dao;
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItem baseItem){
		return baseItemDao.findPageBySql(page, baseItem);
	}
	
	public Page<Map<String,Object>> findListPageBySql(Page<Map<String,Object>> page, BaseItem baseItem){
		return baseItemDao.findListPageBySql(page, baseItem);
	}
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, BaseItem baseItem){
		return baseItemDao.findDetailPageBySql(page, baseItem);
	}

	@Override
	public Page<Map<String, Object>> getItemBaseType(
			Page<Map<String, Object>> page, BaseItem baseItem) {
		// TODO Auto-generated method stub
		return baseItemDao.getItemBaseType(page, baseItem) ;
	}

	@Override
	public List<Map<String, Object>> findBaseItemType(Integer type, String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = baseItemType1Dao.findBaseItemType1(param);
		}else if(type == 2){//基础类型2
			param.put("pCode", pCode);
			resultList = baseItemType2Dao.findBaseItemType2(param);
		}
		return resultList;
	}

	@Override
	public void importDetail(Map<String, Object> data) {
		baseItemDao.importDetail(data);
		
	}

	@Override
	public String getUomByBaseItemCode(String baseItemCode) {
		return baseItemDao.getUomByBaseItemCode(baseItemCode);
	}

	@Override
	public List<Map<String, Object>> getBaseAlgorithmByCode(BaseItem baseItem) {
		return baseItemDao.getBaseAlgorithmByCode(baseItem);
	}

	@Override
	public Result doSave(BaseItem baseItem) {
		return baseItemDao.doSave(baseItem);
	}

	@Override
	public Result doUpdate(BaseItem baseItem) {
		return baseItemDao.doUpdate(baseItem);
	}
	
	@Override
	public Result doUpdatePrice(BaseItem baseItem) {
		return baseItemDao.doUpdatePrice(baseItem);
	}

}
