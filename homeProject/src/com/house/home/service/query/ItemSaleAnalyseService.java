package com.house.home.service.query;

import com.house.framework.commons.orm.BaseService;

import com.house.framework.commons.orm.Page;
import java.util.Map;

import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemType1;

public interface ItemSaleAnalyseService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Item item);

	public Page<Map<String,Object>> findChgPageBySql(Page<Map<String,Object>> page, Item item);

	public Page<Map<String,Object>> findPlanPageBySql(Page<Map<String,Object>> page, Item item);
}
