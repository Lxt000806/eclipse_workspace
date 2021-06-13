package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.ItemPlanTempDao;
import com.house.home.entity.design.ItemPlanTemp;
import com.house.home.service.design.ItemPlanTempService;

@SuppressWarnings("serial")
@Service
public class ItemPlanTempServiceImpl extends BaseServiceImpl implements ItemPlanTempService {

	@Autowired
	private ItemPlanTempDao itemPlanTempDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlanTemp itemPlanTemp){
		return itemPlanTempDao.findPageBySql(page, itemPlanTemp);
	}
	
	public Page<Map<String,Object>> findListPageBySql(Page<Map<String,Object>> page, ItemPlanTemp itemPlanTemp){
		return itemPlanTempDao.findListPageBySql(page, itemPlanTemp);
	}
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, ItemPlanTemp itemPlanTemp){
		return itemPlanTempDao.findDetailPageBySql(page, itemPlanTemp);
	}

	@Override
	public Result doSave(ItemPlanTemp itemPlanTemp) {
		return itemPlanTempDao.doSave(itemPlanTemp);
	}
	
	@Override
	public Result doUpdate(ItemPlanTemp itemPlanTemp) {
		return itemPlanTempDao.doSave(itemPlanTemp);
	}

	
}
