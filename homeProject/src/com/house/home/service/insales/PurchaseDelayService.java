package com.house.home.service.insales;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.PurchaseDelay;

public interface PurchaseDelayService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql
					(Page<Map<String,Object>> page, PurchaseDelay purchaseDelay);

}
