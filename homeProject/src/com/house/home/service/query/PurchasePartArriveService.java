package com.house.home.service.query;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.Purchase;

public interface PurchasePartArriveService extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Purchase purchase);
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, Purchase purchase);

}
