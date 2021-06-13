package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.Purchase;

public interface Dept2FundUseService extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Purchase purchase);
	public Page<Map<String,Object>> findPrepayFeePageBySql(Page<Map<String, Object>> page, Purchase purchase);
	public Page<Map<String,Object>> findLaborFeePageBySql(Page<Map<String, Object>> page,Purchase purchase);
	public  Page<Map<String,Object>> findOtherFeePageBySql(Page<Map<String, Object>> page,Purchase purchase);
	public  Page<Map<String,Object>> findPreAmountPageBySql(Page<Map<String, Object>> page,Purchase purchase);
	public Page<Map<String,Object>> findPurArrFeePageBySql(Page<Map<String, Object>> page,Purchase purchase);

}
