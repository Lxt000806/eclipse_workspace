package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemAppTempDao;
import com.house.home.entity.insales.ItemAppTemp;
import com.house.home.entity.insales.ItemAppTempArea;
import com.house.home.service.insales.ItemAppTempService;

@SuppressWarnings("serial")
@Service
public class ItemAppTempServiceImpl extends BaseServiceImpl implements ItemAppTempService {

	@Autowired
	private ItemAppTempDao itemAppTempDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppTemp itemAppTemp){
		return itemAppTempDao.findPageBySql(page, itemAppTemp);
	}

	@Override
	public Page<Map<String, Object>> getAppItemTypeBatch(
			Page<Map<String, Object>> page, ItemAppTemp itemAppTemp) {
		// TODO Auto-generated method stub
		return itemAppTempDao.getAppItemTypeBatch(page, itemAppTemp);
	}

	@Override
	public Page<Map<String, Object>> findItemAPPTempPageBySql(
			Page<Map<String, Object>> page, ItemAppTemp itemAppTemp) {
		return itemAppTempDao.findItemAPPTempPageBySql(page, itemAppTemp);
	}

	@Override
	public Result doSave(ItemAppTemp itemAppTemp) {
		return itemAppTempDao.doSave(itemAppTemp);
	}

	@Override
	public Page<Map<String, Object>> findAreaPageBySql(
			Page<Map<String, Object>> page, ItemAppTemp itemAppTemp) {
		return itemAppTempDao.findAreaPageBySql(page, itemAppTemp);
	}

	@Override
	public Page<Map<String, Object>> findAreaDetailPageBySql(
			Page<Map<String, Object>> page, ItemAppTempArea itemAppTempArea) {
		return itemAppTempDao.findAreaDetailPageBySql(page, itemAppTempArea);
	}

	@Override
	public Map<String, Object> getItemByCode(String code) {
		return itemAppTempDao.getItemByCode(code);
	}

	@Override
	public Result doAreaSave(ItemAppTempArea itemAppTempArea) {
		return itemAppTempDao.doAreaSave(itemAppTempArea);
	}
	

}
