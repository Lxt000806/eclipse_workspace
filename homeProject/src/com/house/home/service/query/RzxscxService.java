package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.ItemChg;

public interface RzxscxService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemChg itemChg);

	public Page<Map<String,Object>> findItemPlanBySql(Page<Map<String,Object>> page, ItemChg itemChg);

	public Page<Map<String,Object>> findSaleDetailBySql(Page<Map<String,Object>> page, ItemChg itemChg);

	public Page<Map<String,Object>> findItemReqBySql(Page<Map<String,Object>> page, ItemChg itemChg);

	public Page<Map<String,Object>> findChgDetailBySql(Page<Map<String,Object>> page, ItemChg itemChg);

	public String  getYHJEbySql(ItemChg itemChg) ;

	public String  getDLYHbySql(ItemChg itemChg) ;

	public String  getYSYHbySql(ItemChg itemChg) ;
	
	public String getItem1byItem2(String itemType2);
}

