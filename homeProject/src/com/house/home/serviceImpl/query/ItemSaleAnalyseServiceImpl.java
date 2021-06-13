package com.house.home.serviceImpl.query;

import com.house.framework.commons.orm.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.query.ItemSaleAnalyseDao;
import com.house.home.service.query.ItemSaleAnalyseService;

import com.house.framework.commons.orm.Page;
import java.util.Map;

import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemType1;
@SuppressWarnings("serial")
@Service 
public class ItemSaleAnalyseServiceImpl extends BaseServiceImpl implements ItemSaleAnalyseService {
	@Autowired
	private  ItemSaleAnalyseDao itemSaleAnalyseDao;
	@Override	
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, Item item){
	
		return itemSaleAnalyseDao.findPageBySql(page, item);
	}
	@Override
	public Page<Map<String, Object>> findChgPageBySql(
			Page<Map<String, Object>> page, Item item) {
		
		return itemSaleAnalyseDao.findChgPageBySql(page, item);
	}
	@Override
	public Page<Map<String, Object>> findPlanPageBySql(
			Page<Map<String, Object>> page, Item item) {
		
		return itemSaleAnalyseDao.findPlanPageBySql(page, item);
	}
	
	
}
