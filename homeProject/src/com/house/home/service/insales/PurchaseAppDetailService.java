package com.house.home.service.insales;

import com.house.framework.commons.orm.BaseService;

import com.house.framework.commons.orm.Page;
import java.util.Map;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.PurchaseAppDetail;

public interface PurchaseAppDetailService extends BaseService{
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchaseAppDetail purchaseAppDetail);
}
